package wing.tree.multiplication.table.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import wing.tree.multiplication.table.extension.function.wrapInSpaces
import wing.tree.multiplication.table.extension.property.equalsSign

@Composable
internal fun Prompt(
    timesTable: Int,
    multiplicand: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    Row(modifier = modifier) {
        Term(
            timesTable = timesTable,
            multiplicand = multiplicand,
            style = style
        )

        Text(
            text = String.equalsSign.wrapInSpaces(),
            style = style
        )
    }
}
