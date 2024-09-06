package wing.tree.multiplication.table.quiz.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import wing.tree.multiplication.table.ad.InterstitialAdLoader
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.extension.property.isNotFinishing
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.quiz.view.composable.Content
import wing.tree.multiplication.table.quiz.view.composable.TopBar
import wing.tree.multiplication.table.quiz.view.model.QuizViewModel
import wing.tree.multiplication.table.theme.MultiplicationTableTheme

class QuizActivity : ComponentActivity() {
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

        InterstitialAdLoader.load(this)

        setContent {
            MultiplicationTableTheme(activity = this) {
                val state by viewModel.state.collectAsState()

                Scaffold(
                    topBar = {
                        TopBar(
                            navigationOnClick = {
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
