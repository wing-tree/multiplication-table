package wing.tree.multiplication.table.composition.local

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.compositionLocalOf

val localWindowWidthSizeClass = compositionLocalOf<WindowWidthSizeClass> {
    error(WindowWidthSizeClass::class)
}
