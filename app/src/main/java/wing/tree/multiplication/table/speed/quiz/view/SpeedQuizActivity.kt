package wing.tree.multiplication.table.speed.quiz.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import wing.tree.multiplication.table.ad.InterstitialAdLoader
import wing.tree.multiplication.table.ad.composable.AdLoading
import wing.tree.multiplication.table.composable.Crossfade
import wing.tree.multiplication.table.extension.property.`7`
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.extension.property.isNotFinishing
import wing.tree.multiplication.table.extension.property.twice
import wing.tree.multiplication.table.dialog.intent.DialogState
import wing.tree.multiplication.table.speed.quiz.action.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.side.effect.SpeedQuizSideEffect
import wing.tree.multiplication.table.speed.quiz.state.SpeedQuizState
import wing.tree.multiplication.table.speed.quiz.view.composable.Play
import wing.tree.multiplication.table.speed.quiz.view.composable.Ready
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
            MultiplicationTableTheme(activity = this) {
                val sideEffect by viewModel.sideEffect.collectAsState(null)
                val state by viewModel.state.collectAsState()

                var dialogState by remember {
                    mutableStateOf<DialogState>(DialogState.Dismissed)
                }

                LaunchedEffect(sideEffect) {
                    sideEffect?.let {
                        when (it) {
                            SpeedQuizSideEffect.Home -> if (isNotFinishing) {
                                finish()
                            }

                            SpeedQuizSideEffect.Show.InterstitialAd -> {
                                dialogState = DialogState.Showing

                                delay(timeMillis = Long.`7`.hundreds.twice)

                                InterstitialAdLoader.show(
                                    activity = this@SpeedQuizActivity
                                )

                                dialogState = DialogState.Dismissed
                            }
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
                                onAction = viewModel::onAction
                            )

                            is SpeedQuizState.Ready -> Ready(state = targetState) {
                                viewModel.onAction(action = SpeedQuizAction.OnReady)
                            }
                        }
                    }
                }

                AdLoading(state = dialogState) {
                    dialogState = DialogState.Dismissed
                }
            }
        }
    }
}