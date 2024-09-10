package wing.tree.multiplication.table.main.view.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import wing.tree.multiplication.table.composable.MultiplicationTableRow
import wing.tree.multiplication.table.composable.VerticalSpacer
import wing.tree.multiplication.table.extension.function.incrementByTwo
import wing.tree.multiplication.table.extension.function.quadrupled
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.token.Space
import wing.tree.multiplication.table.top.level.property.MINIMUM_TIMES_TABLE

@Composable
internal fun PageContent(
    page: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val timesTable = MINIMUM_TIMES_TABLE.plus(page.quadrupled())

        MultiplicationTableRow(
            timesTable = timesTable,
            modifier = Modifier.weight(weight = Float.`1`)
        )

        VerticalSpacer(height = Space.extra.small)

        MultiplicationTableRow(
            timesTable = timesTable.incrementByTwo(),
            modifier = Modifier.weight(weight = Float.`1`)
        )
    }
}
