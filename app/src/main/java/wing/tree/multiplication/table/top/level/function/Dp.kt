package wing.tree.multiplication.table.top.level.function

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.extension.function.width
import wing.tree.multiplication.table.extension.property.widestDigit
import wing.tree.multiplication.table.extension.property.widestPrompt
import wing.tree.multiplication.table.top.level.property.MAXIMUM_DIGITS

@Composable
internal fun widestMultiplicationWidth(style: TextStyle = LocalTextStyle.current): Dp {
    val maxWidth = "${Char.widestDigit}".width(style)
    val width = String.widestPrompt
        .width(style)
        .plus(maxWidth.times(MAXIMUM_DIGITS))

    return width
}
