package wing.tree.multiplication.table.composable

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import wing.tree.multiplication.table.extension.empty
import wing.tree.multiplication.table.extension.function.`this`

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <T : Any?> Crossfade(
    targetState: T,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    contentKey: (T) -> Any? = Any?::`this`,
    content: @Composable (T) -> Unit
) {
    updateTransition(
        targetState = targetState,
        label = String.empty
    )
        .Crossfade(
            modifier = modifier,
            animationSpec = animationSpec,
            contentKey = contentKey,
            content = content
        )
}
