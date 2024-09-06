package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import wing.tree.multiplication.table.composable.Crossfade
import wing.tree.multiplication.table.speed.quiz.action.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.state.SpeedQuizState

@Composable
internal fun Play(
    state: SpeedQuizState.Play,
    onAction: (SpeedQuizAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Crossfade(
        targetState = state,
        modifier = modifier
    ) { targetState ->
        when (targetState) {
            is SpeedQuizState.Play.Played -> Played(
                state = targetState,
                onAction = onAction
            )

            is SpeedQuizState.Play.Playing -> SpeedQuiz(
                speedQuiz = targetState.speedQuiz,
                onAction = onAction,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}