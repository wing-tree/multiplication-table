@file:Suppress("unused")

package wing.tree.multiplication.table.extension

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp

@Composable
fun List<Char>.rememberMaxHeight(
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true
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
                    softWrap = softWrap
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
    softWrap: Boolean = true
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
                    softWrap = softWrap
                )
                    .size
                    .width
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
