package wing.tree.multiplication.table.main.view.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.composable.MultiplicationTableRow
import wing.tree.multiplication.table.composable.VerticalSpacer
import wing.tree.multiplication.table.constant.MINIMUM_TIMES_TABLE
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.full
import wing.tree.multiplication.table.extension.incrementByTwo
import wing.tree.multiplication.table.extension.quadrupled

@Composable
internal fun PageContent(
    page: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val timesTable = MINIMUM_TIMES_TABLE.plus(page.quadrupled())

        MultiplicationTableRow(
            timesTable = timesTable,
            modifier = Modifier.weight(Float.full)
        )

        VerticalSpacer(height = Dp.extraSmall)

        MultiplicationTableRow(
            timesTable = timesTable.incrementByTwo(),
            modifier = Modifier.weight(Float.full)
        )
    }
}
