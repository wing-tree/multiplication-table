package wing.tree.multiplication.table.extension

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp

private const val EMPTY = ""

val String.Companion.empty: String get() = EMPTY

val String.intOrNull: Int? get() = toIntOrNull()

fun String.fourth() = get(Int.fourthIndex)

@Composable
fun String.rememberWidth(style: TextStyle = LocalTextStyle.current): Dp {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    return remember(textMeasurer) {
        val size = textMeasurer.measure(
            text = this,
            style = style
        )
            .size

        with(density) {
            size.width.toDp()
        }
    }
}

@Composable
fun String.width(style: TextStyle = LocalTextStyle.current): Dp {
    val density = LocalDensity.current

    val size = rememberTextMeasurer().measure(
        text = this,
        style = style
    )
        .size

    return with(density) {
        size.width.toDp()
    }
}

fun String.second() = get(Int.secondIndex)
fun String.third() = get(Int.thirdIndex)
