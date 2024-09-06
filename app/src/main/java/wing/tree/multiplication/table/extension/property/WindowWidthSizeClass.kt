package wing.tree.multiplication.table.extension.property

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.extension.extraLarge
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.not
import wing.tree.multiplication.table.extension.medium

val WindowWidthSizeClass.isCompact: Boolean get() = `is`(WindowWidthSizeClass.Compact)
val WindowWidthSizeClass.isNotCompact: Boolean get() = not(WindowWidthSizeClass.Compact)
val WindowWidthSizeClass.paddingValues: PaddingValues
    get() = when (this) {
        WindowWidthSizeClass.Compact -> PaddingValues(horizontal = Dp.medium)
        WindowWidthSizeClass.Medium -> PaddingValues(horizontal = Dp.extraLarge)
        WindowWidthSizeClass.Expanded -> PaddingValues(horizontal = Dp.extraLarge)
        else -> PaddingValues(horizontal = Dp.medium)
    }
