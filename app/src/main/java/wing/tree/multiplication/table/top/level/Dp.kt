package wing.tree.multiplication.table.top.level

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import wing.tree.multiplication.table.extension.function.repeat
import wing.tree.multiplication.table.extension.function.width
import wing.tree.multiplication.table.extension.function.wrapInSpaces
import wing.tree.multiplication.table.extension.property.equalsSign
import wing.tree.multiplication.table.extension.property.multiplicationSign
import wing.tree.multiplication.table.extension.property.widestDigit
import wing.tree.multiplication.table.extension.property.widestPrompt
import wing.tree.multiplication.table.top.level.property.MAXIMUM_DIGITS
import wing.tree.multiplication.table.top.level.property.MAXIMUM_MULTIPLICAND

@Composable
internal fun multiplicationWidthFor(
    timesTable: Int,
    style: TextStyle = LocalTextStyle.current
): Dp {
    val widestDigitWidth = "${Char.widestDigit}".width(style)
    val product = timesTable.times(MAXIMUM_MULTIPLICAND)
    val width = buildString {
        append(Char.widestDigit.repeat("$timesTable".length))
        append(String.equalsSign.wrapInSpaces())
        append(Char.widestDigit)
        append(String.multiplicationSign.wrapInSpaces())
    }
        .width(style = style)
        .plus(other = "$product".length.times(widestDigitWidth))

    return width
}

@Composable
internal fun widestMultiplicationWidth(style: TextStyle = LocalTextStyle.current): Dp {
    val maxWidth = "${Char.widestDigit}".width(style)
    val width = String.widestPrompt
        .width(style)
        .plus(maxWidth.times(MAXIMUM_DIGITS))

    return width
}
