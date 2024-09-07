package wing.tree.multiplication.table.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import wing.tree.multiplication.table.extension.function.width
import wing.tree.multiplication.table.extension.function.wrapInSpaces
import wing.tree.multiplication.table.extension.property.multiplicationSign
import wing.tree.multiplication.table.extension.property.widestDigit

@Composable
internal fun Term(
    timesTable: Int,
    multiplicand: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    Row(modifier = modifier) {
        val widestDigitWidth = Char.widestDigit.width(style)

        Text(
            text = "$timesTable",
            modifier = Modifier.width(widestDigitWidth.times("$timesTable".length)),
            textAlign = TextAlign.Center,
            style = style
        )

        Text(
            text = String.multiplicationSign.wrapInSpaces(),
            style = style
        )

        Text(
            text = "$multiplicand",
            modifier = Modifier.width(widestDigitWidth),
            textAlign = TextAlign.Center,
            style = style
        )
    }
}
