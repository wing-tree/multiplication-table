package wing.tree.multiplication.table.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.top.level.property.fillMaxHeight

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
            modifier = fillMaxHeight.weight(
                weight = Float.`1`,
                fill = false
            )
        )

        HorizontalSpacer(width = Dp.extraSmall)

        MultiplicationTable(
            timesTable = timesTable.inc(),
            modifier = fillMaxHeight.weight(
                weight = Float.`1`,
                fill = false
            )
        )
    }
}
