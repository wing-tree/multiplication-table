package wing.tree.multiplication.table.extension

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.collections.immutable.persistentListOf

val shimmerTheme = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            1_000,
            easing = LinearEasing,
            delayMillis = 1_000,
        ),
        repeatMode = RepeatMode.Restart,
    ),
    blendMode = BlendMode.DstOut,
    shaderColors = persistentListOf(
        Color.Unspecified.copy(alpha = Float.fullyTransparent),
        Color.Unspecified.copy(alpha = 0.89F),
        Color.Unspecified.copy(alpha = Float.fullyTransparent),
    )
)

fun Modifier.shimmer() = composed {
    shimmer(
        rememberShimmer(
            shimmerBounds = ShimmerBounds.View,
            theme = shimmerTheme
        )
    )
}
