package wing.tree.multiplication.table.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.full

@Composable
internal fun MultiplicationTableRow(
    timesTable: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        MultiplicationTable(
            timesTable = timesTable,
            modifier = Modifier
                .weight(Float.full)
                .fillMaxHeight()
        )

        HorizontalSpacer(width = Dp.extraSmall)

        MultiplicationTable(
            timesTable = timesTable.inc(),
            modifier = Modifier
                .weight(Float.full)
                .fillMaxHeight()
        )
    }
}
