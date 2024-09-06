package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import wing.tree.multiplication.table.constant.MULTIPLICATION_SIGN
import wing.tree.multiplication.table.extension.maxWidth
import wing.tree.multiplication.table.extension.property.digit

@Composable
internal fun Multiplication(
    timesTable: Int,
    multiplicand: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    val maxWidth = Char.digit.maxWidth(style)

    Row(modifier = modifier) {
        Text(
            text = "$timesTable",
            modifier = Modifier.width(maxWidth.times("$timesTable".length)),
            textAlign = TextAlign.Center,
            style = style
        )

        Text(
            text = " $MULTIPLICATION_SIGN ",
            style = style
        )

        Text(
            text = "$multiplicand",
            modifier = Modifier.width(maxWidth),
            textAlign = TextAlign.Center,
            style = style
        )
    }
}
