@file:Suppress("unused")

package wing.tree.multiplication.table.extension

import android.graphics.Rect
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
import wing.tree.multiplication.table.extension.property.single

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

@Composable
fun List<Char>.rememberMaxWidth(
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.single
): Dp {
    val context = LocalContext.current
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val textPaint = remember(textMeasurer) {
        TextPaint().also {
            it.typeface = ResourcesCompat.getFont(context, R.font.y_clover_regular)
            it.textSize = density.run { style.fontSize.toPx() }
            val letterSpacingEm = style.letterSpacing.value  // letterSpacing의 em 값
            val letterSpacingPx = letterSpacingEm * it.textSize  // letterSpacing을 px로 변환
            it.letterSpacing = (letterSpacingPx / it.textSize).div(10f)  // px 단위를 em에 맞춰 설정
        }
    }

println("wwwwwww333:${style.letterSpacing}}")
//println("wwwwwwww00:${textPaint.letterSpacing}")
    return remember(textMeasurer) {
        with(density) {
            maxOf {
                val rect = floatArrayOf(0f)
                textPaint.getTextWidths(it.toString(), 0, 1, rect)
                rect.first().also {
                    println("wwwwwww1:$it,,, ${it.roundedInt}")
                }
                textMeasurer.measure(
                    text = "$it",
                    style = style,
                    overflow = overflow,
                    softWrap = softWrap,
                    maxLines = maxLines
                )
                    .size
                    .width.also {
                        println("wwwwwww2:$it")
                    }
            }
                .toDp()
        }
    }
}

fun <T> List<T>.fifth() = get(Int.fifthIndex)
fun <T> List<T>.fourth() = get(Int.fourthIndex)
fun <T> List<T>.second() = get(Int.secondIndex)
fun <T> List<T>.seventh() = get(Int.seventhIndex)
fun <T> List<T>.sixth() = get(Int.sixthIndex)
fun <T> List<T>.third() = get(Int.thirdIndex)
