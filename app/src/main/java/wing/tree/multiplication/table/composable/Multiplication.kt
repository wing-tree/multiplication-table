package wing.tree.multiplication.table.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import wing.tree.multiplication.table.extension.function.width
import wing.tree.multiplication.table.extension.property.widestDigit
import wing.tree.multiplication.table.top.level.property.MAXIMUM_MULTIPLICAND

@Composable
internal fun Multiplication(
    timesTable: Int,
    multiplicand: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    val widestDigitWidth = "${Char.widestDigit}".width(style)
    val product = timesTable.times(multiplicand)

    Row(modifier = modifier) {
        Prompt(
            timesTable = timesTable,
            multiplicand = multiplicand,
            style = style
        )

        Text(
            text = "$product",
            modifier = Modifier.width(
                widestDigitWidth.times("${timesTable.times(MAXIMUM_MULTIPLICAND)}".length)
            ),
            style = style
        )
    }
}
