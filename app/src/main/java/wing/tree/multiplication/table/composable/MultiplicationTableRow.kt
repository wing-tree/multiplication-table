package wing.tree.multiplication.table.composable

import androidx.compose.foundation.layout.Arrangement
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
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        MultiplicationTable(
            timesTable = timesTable,
            modifier = Modifier
                .fillMaxHeight()
                .weight(weight = Float.full, fill = false)
        )

        HorizontalSpacer(width = Dp.extraSmall)

        MultiplicationTable(
            timesTable = timesTable.inc(),
            modifier = Modifier
                .fillMaxHeight()
                .weight(weight = Float.full, fill = false)
        )
    }
}
