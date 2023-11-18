package wing.tree.multiplication.table.extension

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.Dp
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowLayoutInfo
import wing.tree.multiplication.table.model.MarginValues

val WindowWidthSizeClass.isNotCompact: Boolean get() = not(WindowWidthSizeClass.Compact)

val WindowWidthSizeClass.marginValues: MarginValues get() = when (this) {
    WindowWidthSizeClass.Compact -> MarginValues(horizontal = Dp.medium)
    WindowWidthSizeClass.Medium -> MarginValues(horizontal = Dp.extraLarge)
    WindowWidthSizeClass.Expanded -> MarginValues(horizontal = Dp.extraLarge)
    else -> MarginValues(horizontal = Dp.medium)
}

fun WindowWidthSizeClass.isNavigationRailNotVisible(windowLayoutInfo: WindowLayoutInfo?): Boolean =
    isNavigationRailVisible(windowLayoutInfo).not()

fun WindowWidthSizeClass.isNavigationRailVisible(windowLayoutInfo: WindowLayoutInfo?): Boolean {
    val displayFeatures = windowLayoutInfo?.displayFeatures ?: emptyList()

    return when {
        isNotCompact -> true
        else -> displayFeatures.filterIsInstance<FoldingFeature>().any {
            when (it.state) {
                FoldingFeature.State.FLAT, FoldingFeature.State.HALF_OPENED -> true
                else -> false
            }
        }
    }
}
