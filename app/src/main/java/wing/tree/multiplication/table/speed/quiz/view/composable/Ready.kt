package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import wing.tree.multiplication.table.extension.property.`7`
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.speed.quiz.state.SpeedQuizState

@Composable
internal fun Ready(
    state: SpeedQuizState.Ready,
    onReady: () -> Unit
) {
    LaunchedEffect(key1 = state) {
        delay(timeMillis = Long.`7`.hundreds)

        onReady()
    }
}
