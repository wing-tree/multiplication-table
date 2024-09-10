package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import kotlinx.coroutines.delay
import wing.tree.multiplication.table.extension.property.`7`
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.speed.quiz.state.SpeedQuizState

@Composable
internal fun Preparing(
    state: SpeedQuizState.Preparing,
    modifier: Modifier = Modifier,
    onPrepared: () -> Unit
) {
    LaunchedEffect(key1 = state) {
        delay(timeMillis = Long.`7`.hundreds)

        onPrepared()
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(strokeCap = StrokeCap.Round)
    }
}
