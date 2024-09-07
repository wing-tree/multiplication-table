package wing.tree.multiplication.table.extension.function

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp

@Composable
fun PaddingValues.copy(
    start: Dp = calculateStartPadding(LocalLayoutDirection.current),
    top: Dp = calculateTopPadding(),
    end: Dp = calculateEndPadding(LocalLayoutDirection.current),
    bottom: Dp = calculateBottomPadding()
) = PaddingValues(
    start = start,
    top = top,
    end = end,
    bottom = bottom
)

@ReadOnlyComposable
@Composable
operator fun PaddingValues.plus(other: PaddingValues) {
    val layoutDirection = LocalLayoutDirection.current

    PaddingValues(
        start = calculateStartPadding(layoutDirection).plus(other.calculateStartPadding(layoutDirection)),
        top = calculateTopPadding().plus(other.calculateTopPadding()),
        end = calculateEndPadding(layoutDirection).plus(other.calculateEndPadding(layoutDirection)),
        bottom = calculateBottomPadding().plus(other.calculateBottomPadding())
    )
}
