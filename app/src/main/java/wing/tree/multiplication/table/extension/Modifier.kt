package wing.tree.multiplication.table.extension

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.collections.immutable.persistentListOf
import kotlin.math.min

private val shimmerTheme = defaultShimmerTheme.copy(
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

private fun ContentDrawScope.drawBottomFadingEdge(
    scrollState: ScrollState,
    length: Dp
) = with(scrollState) {
    val endY = size.height.minus(maxValue).plus(value)
    val fadingEdgeLength = min(length.toPx(), maxValue.minus(value).float)
    val startY = endY.minus(fadingEdgeLength)

    if (fadingEdgeLength.isNotZero()) {
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Black, Color.Transparent),
                startY = startY,
                endY = endY
            ),
            blendMode = BlendMode.DstIn
        )
    }
}

private fun ContentDrawScope.drawTopFadingEdge(
    scrollState: ScrollState,
    length: Dp
) {
    val startY = scrollState.value.float
    val endY = startY.plus(min(length.toPx(), startY))

    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black),
            startY = startY,
            endY = endY
        ),
        blendMode = BlendMode.DstIn
    )
}

fun Modifier.shimmer() = composed {
    shimmer(
        rememberShimmer(
            shimmerBounds = ShimmerBounds.View,
            theme = shimmerTheme
        )
    )
}

fun Modifier.verticalFadingEdge(
    scrollState: ScrollState,
    length: Dp = Dp.medium
): Modifier = then(
    Modifier
        .graphicsLayer {
            alpha = with(Float) {
                full.minus(onePercent)
            }
        }.drawWithContent {
            drawContent()

            drawTopFadingEdge(
                scrollState = scrollState,
                length = length
            )

            drawBottomFadingEdge(
                scrollState = scrollState,
                length = length
            )
        }
)
