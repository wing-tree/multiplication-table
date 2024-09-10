package wing.tree.multiplication.table.extension.function

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.collections.immutable.persistentListOf
import wing.tree.multiplication.table.constant.LowContrastContentAlpha
import wing.tree.multiplication.table.extension.property.`0`
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`8`
import wing.tree.multiplication.table.extension.property.empty
import wing.tree.multiplication.table.extension.property.float
import wing.tree.multiplication.table.extension.property.fullyOpaque
import wing.tree.multiplication.table.extension.property.fullyTransparent
import wing.tree.multiplication.table.extension.property.isNotZero
import wing.tree.multiplication.table.extension.property.onePercent
import wing.tree.multiplication.table.extension.property.oneSecondInMilliseconds
import wing.tree.multiplication.table.model.Percentage
import wing.tree.multiplication.table.token.Padding
import kotlin.math.min

private val shimmerTheme = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = Int.oneSecondInMilliseconds,
            easing = LinearEasing,
            delayMillis = Int.oneSecondInMilliseconds
        ),
        repeatMode = RepeatMode.Restart,
    ),
    blendMode = BlendMode.DstOut,
    shaderColors = persistentListOf(
        Color.Unspecified.copy(alpha = Float.fullyTransparent),
        Color.Unspecified.copy(alpha = LowContrastContentAlpha.high),
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

fun Modifier.verticalFadingEdge(
    length: Dp = Dp.`8`,
    visible: Boolean = true,
    animationSpec: AnimationSpec<Dp>? = null
) = graphicsLayer {
    alpha = Float.fullyOpaque.minus(Percentage.`1`)
}
    .composed {
        require(length isGreaterThan Dp.`0`)

        val value = with(LocalDensity.current) {
            animationSpec?.let {
                animateDpAsState(
                    targetValue = when {
                        visible -> length
                        else -> Dp.`0`
                    },
                    animationSpec = it,
                    label = String.empty
                )
                    .value
                    .toPx()
            } ?: when {
                visible -> length.toPx()
                else -> Float.`0`
            }
        }

        drawWithContent {
            drawContent()

            with(size) {
                val colorStops = arrayOf(
                    Float.`0` to Color.Transparent,
                    value.div(height) to Black
                )

                drawRect(
                    brush = Brush.linearGradient(
                        colorStops = colorStops,
                        start = Offset.Zero,
                        end = Offset(Float.`0`, height)
                    ),
                    blendMode = BlendMode.DstIn,
                    size = size
                )

                drawRect(
                    brush = Brush.linearGradient(
                        colorStops = colorStops,
                        start = Offset(Float.`0`, height),
                        end = Offset.Zero
                    ),
                    blendMode = BlendMode.DstIn,
                    size = size
                )
            }
        }
    }

fun Modifier.verticalFadingEdge(
    scrollState: ScrollState,
    length: Dp = Padding.small
): Modifier = then(
    Modifier
        .graphicsLayer {
            alpha = with(Float) {
                `1`.minus(onePercent)
            }
        }
        .drawWithContent {
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

private fun ContentDrawScope.drawBottomFadingEdge(
    scrollState: ScrollState,
    length: Dp
) = with(scrollState) {
    val endY = size.height.minus(maxValue).plus(value)
    val fadingEdgeLength = min(length.toPx(), maxValue.minus(value).float)
    val startY = endY.minus(fadingEdgeLength)

    if (fadingEdgeLength.isNotZero) {
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Black, Color.Transparent),
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
            colors = listOf(Color.Transparent, Black),
            startY = startY,
            endY = endY
        ),
        blendMode = BlendMode.DstIn
    )
}
