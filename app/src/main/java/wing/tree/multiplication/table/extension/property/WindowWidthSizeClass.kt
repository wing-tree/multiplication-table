package wing.tree.multiplication.table.extension.property

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.extension.extraLarge
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.not
import wing.tree.multiplication.table.extension.medium
import wing.tree.multiplication.table.model.MarginValues

val WindowWidthSizeClass.isCompact: Boolean get() = `is`(WindowWidthSizeClass.Compact)
val WindowWidthSizeClass.isNotCompact: Boolean get() = not(WindowWidthSizeClass.Compact)
val WindowWidthSizeClass.marginValues: MarginValues get() = when (this) {
    WindowWidthSizeClass.Compact -> MarginValues(horizontal = Dp.medium)
    WindowWidthSizeClass.Medium -> MarginValues(horizontal = Dp.extraLarge)
    WindowWidthSizeClass.Expanded -> MarginValues(horizontal = Dp.extraLarge)
    else -> MarginValues(horizontal = Dp.medium)
}
