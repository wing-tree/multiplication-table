@file:Suppress("unused")

package wing.tree.multiplication.table.extension

import android.text.TextPaint
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.core.content.res.ResourcesCompat
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.extension.property.`0`
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`2`
import wing.tree.multiplication.table.extension.property.`3`
import wing.tree.multiplication.table.extension.property.`4`
import wing.tree.multiplication.table.extension.property.`5`
import wing.tree.multiplication.table.extension.property.`6`
import wing.tree.multiplication.table.extension.property.single
import kotlin.math.ceil

@Composable
fun List<Char>.maxWidth(style: TextStyle = LocalTextStyle.current): Dp {
    val context = LocalContext.current
    val density = LocalDensity.current
    val floatArray = remember {
        FloatArray(Int.`1`)
    }

    val textPaint = remember {
        TextPaint().also {
            it.typeface = ResourcesCompat.getFont(context, R.font.y_clover_regular)
            it.letterSpacing = 0.05F
        }
    }

    textPaint.textSize = with(density) {
        style.fontSize.toPx()
    }

    return with(density) {
        maxOf {
            textPaint.getTextWidths(it.toString(), Int.`0`, Int.`1`, floatArray)
            ceil(floatArray.single())
        }
            .toDp()
    }
}

@Composable
fun List<Char>.rememberMaxHeight(
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.single
): Dp {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    return remember(textMeasurer) {
        with(density) {
            maxOf {
                textMeasurer.measure(
                    text = "$it",
                    style = style,
                    overflow = overflow,
                    softWrap = softWrap,
                    maxLines = maxLines
                )
                    .size
                    .height
            }
                .toDp()
        }
    }
}

fun <T> List<T>.fifth() = get(Int.`4`)
fun <T> List<T>.fourth() = get(Int.`3`)
fun <T> List<T>.second() = get(Int.`1`)
fun <T> List<T>.seventh() = get(Int.`6`)
fun <T> List<T>.sixth() = get(Int.`5`)
fun <T> List<T>.third() = get(Int.`2`)
