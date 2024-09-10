package wing.tree.multiplication.table.speed.quiz.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.ad.InterstitialAdLoader
import wing.tree.multiplication.table.ad.composable.ProgressDialog
import wing.tree.multiplication.table.composable.Crossfade
import wing.tree.multiplication.table.dialog.intent.DialogState
import wing.tree.multiplication.table.dialog.model.Dialog
import wing.tree.multiplication.table.extension.property.`7`
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.extension.property.isNotFinishing
import wing.tree.multiplication.table.extension.property.twice
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizSideEffect
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizState
import wing.tree.multiplication.table.speed.quiz.view.composable.Play
import wing.tree.multiplication.table.speed.quiz.view.composable.Preparing
import wing.tree.multiplication.table.speed.quiz.view.composable.TopBar
import wing.tree.multiplication.table.speed.quiz.view.model.SpeedQuizViewModel
import wing.tree.multiplication.table.theme.MultiplicationTableTheme
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.property.fillMaxSize

class SpeedQuizActivity : ComponentActivity() {
    private val viewModel by viewModels<SpeedQuizViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InterstitialAdLoader.load(context = this)

        setContent {
            MultiplicationTableTheme(this) {
                val state by viewModel.state.collectAsState()

                var dialogState by remember {
                    mutableStateOf<DialogState<Dialog.Progress>>(DialogState.Dismissed)
                }

                viewModel.collectSideEffect { sideEffect ->
                    when (sideEffect) {
                        SpeedQuizSideEffect.Home -> if (isNotFinishing) {
                            finish()
                        }

                        SpeedQuizSideEffect.Show.InterstitialAd -> {
                            dialogState = DialogState.Showing(
                                Dialog.Progress(getString(R.string.ad_loading))
                            )

                            delay(timeMillis = Long.`7`.hundreds.twice)

                            InterstitialAdLoader.show(
                                activity = this@SpeedQuizActivity
                            )

                            dialogState = DialogState.Dismissed
                        }
                    }
                }

                Scaffold(
                    topBar = {
                        TopBar(
                            state = state,
                            navigationOnClick = {
                                if (isNotFinishing) {
                                    finish()
                                }
                            }
                        )
                    },
                    modifier = fillMaxSize
                ) { paddingValues ->
                    Crossfade(
                        targetState = state,
                        modifier = Modifier
                            .padding(paddingValues = paddingValues)
                            .padding(vertical = Padding.medium)
                    ) { targetState ->
                        when (targetState) {
                            is SpeedQuizState.Play -> Play(
                                state = targetState,
                                modifier = fillMaxSize,
                                onAction = viewModel::onAction
                            )

                            is SpeedQuizState.Preparing -> Preparing(
                                state = targetState,
                                modifier = fillMaxSize
                            ) {
                                viewModel.onAction(action = SpeedQuizAction.OnReady)
                            }
                        }
                    }
                }

                ProgressDialog(state = dialogState) {
                    dialogState = DialogState.Dismissed
                }
            }
        }
    }
}