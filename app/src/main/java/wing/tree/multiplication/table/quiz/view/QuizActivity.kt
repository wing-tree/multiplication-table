package wing.tree.multiplication.table.quiz.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import wing.tree.multiplication.table.ad.InterstitialAdLoader
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.extension.isNotFinishing
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.quiz.view.composable.Content
import wing.tree.multiplication.table.quiz.view.composable.TopBar
import wing.tree.multiplication.table.quiz.view.model.QuizViewModel
import wing.tree.multiplication.table.theme.MultiplicationTableTheme

class QuizActivity : ComponentActivity() {
    private val activity = this
    private val interstitialAdLoader = InterstitialAdLoader()
    private val onAction: (Action) -> Unit = {
        when (it) {
            Action.Check -> viewModel.check()
            Action.SolveAgain -> viewModel.solveAgain()
            else -> noOperations
        }
    }

    private val viewModel by viewModels<QuizViewModel>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        interstitialAdLoader.load(this)
        setContent {
            MultiplicationTableTheme {
                val coroutineScope = rememberCoroutineScope()
                val state by viewModel.state.collectAsState()

                BackHandler(viewModel.solvedAtLeastOnce) {
                    coroutineScope.launch {
                        interstitialAdLoader.show(activity = activity)
                    }

                    if (isNotFinishing) {
                        finish()
                    }
                }

                Scaffold(
                    topBar = {
                        TopBar(
                            navigationOnClick = {
                                if (viewModel.solvedAtLeastOnce) {
                                    coroutineScope.launch {
                                        interstitialAdLoader.show(activity = activity)
                                    }
                                }

                                if (isNotFinishing) {
                                    finish()
                                }
                            }
                        )
                    }
                ) {
                    val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass

                    Content(
                        state = state,
                        widthSizeClass = widthSizeClass,
                        onAction = onAction,
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }
    }
}
