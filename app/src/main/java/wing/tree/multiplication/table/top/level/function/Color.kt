package wing.tree.multiplication.table.top.level.function

import androidx.compose.ui.graphics.Color
import wing.tree.multiplication.table.top.level.property.MINIMUM_TIMES_TABLE
import wing.tree.multiplication.table.theme.palette

fun containerColor(timesTable: Int): Color = with(palette) {
    val index = timesTable.minus(MINIMUM_TIMES_TABLE).rem(size)

    get(index)
}
