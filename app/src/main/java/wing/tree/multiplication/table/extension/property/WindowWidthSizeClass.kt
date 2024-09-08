package wing.tree.multiplication.table.extension.property

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.not
import wing.tree.multiplication.table.token.Padding

val WindowWidthSizeClass.isCompact: Boolean get() = `is`(WindowWidthSizeClass.Compact)
val WindowWidthSizeClass.isNotCompact: Boolean get() = not(WindowWidthSizeClass.Compact)
val WindowWidthSizeClass.paddingValues: PaddingValues
    get() = when (this) {
        WindowWidthSizeClass.Compact -> PaddingValues(horizontal = Padding.medium)
        WindowWidthSizeClass.Medium -> PaddingValues(horizontal = Padding.large)
        WindowWidthSizeClass.Expanded -> PaddingValues(horizontal = Padding.large)
        else -> PaddingValues(horizontal = Padding.medium)
    }
