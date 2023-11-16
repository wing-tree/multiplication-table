package wing.tree.multiplication.table.extension

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.model.MarginValues

val WindowWidthSizeClass.marginValues: MarginValues get() = when (this) {
    WindowWidthSizeClass.Compact -> MarginValues(horizontal = Dp.medium)
    WindowWidthSizeClass.Medium -> MarginValues(horizontal = Dp.extraLarge)
    WindowWidthSizeClass.Expanded -> MarginValues(horizontal = Dp.extraLarge)
    else -> MarginValues(horizontal = Dp.medium)
}
