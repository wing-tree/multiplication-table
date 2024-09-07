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
import androidx.compose.ui.Modifier
import wing.tree.multiplication.table.composable.Crossfade
import wing.tree.multiplication.table.extension.property.isNotFinishing
import wing.tree.multiplication.table.speed.quiz.action.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.side.effect.SpeedQuizSideEffect
import wing.tree.multiplication.table.speed.quiz.state.SpeedQuizState
import wing.tree.multiplication.table.speed.quiz.view.composable.Play
import wing.tree.multiplication.table.speed.quiz.view.composable.Ready
import wing.tree.multiplication.table.speed.quiz.view.composable.TopBar
import wing.tree.multiplication.table.speed.quiz.view.model.SpeedQuizViewModel
import wing.tree.multiplication.table.theme.MultiplicationTableTheme
import wing.tree.multiplication.table.top.level.property.fillMaxSize

class SpeedQuizActivity : ComponentActivity() {
    private val viewModel by viewModels<SpeedQuizViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiplicationTableTheme(activity = this) {
                val sideEffect by viewModel.sideEffect.collectAsState(null)
                val state by viewModel.state.collectAsState()

                LaunchedEffect(sideEffect) {
                    sideEffect?.let {
                        when (it) {
                            SpeedQuizSideEffect.Home -> if (isNotFinishing) {
                                finish()
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
                        modifier = Modifier.padding(paddingValues = paddingValues)
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
            }
        }
    }
}