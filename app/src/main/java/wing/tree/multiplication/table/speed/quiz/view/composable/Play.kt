package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import wing.tree.multiplication.table.composable.Crossfade
import wing.tree.multiplication.table.composition.local.localWindowWidthSizeClass
import wing.tree.multiplication.table.extension.property.paddingValues
import wing.tree.multiplication.table.speed.quiz.action.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.state.SpeedQuizState
import wing.tree.multiplication.table.top.level.property.fillMaxSize

@Composable
internal fun Play(
    state: SpeedQuizState.Play,
    onAction: (SpeedQuizAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val windowWidthSizeClass = localWindowWidthSizeClass.current

    Crossfade(
        targetState = state,
        modifier = modifier
    ) { targetState ->
        when (targetState) {
            is SpeedQuizState.Play.Played -> Played(
                state = targetState,
                onAction = onAction,
                modifier = fillMaxSize.padding(
                    paddingValues = windowWidthSizeClass.paddingValues
                )
            )

            is SpeedQuizState.Play.Playing -> SpeedQuiz(
                speedQuiz = targetState.speedQuiz,
                onAction = onAction,
                modifier = fillMaxSize.padding(
                    paddingValues = windowWidthSizeClass.paddingValues
                )
            )
        }
    }
}