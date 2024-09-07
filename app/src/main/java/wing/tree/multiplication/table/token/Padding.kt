package wing.tree.multiplication.table.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.extension.`8`
import wing.tree.multiplication.table.extension.half
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.inc
import wing.tree.multiplication.table.extension.property.pair
import wing.tree.multiplication.table.extension.property.single
import wing.tree.multiplication.table.extension.property.triple

internal abstract class Padding(open val dp: Dp) {
    abstract val extra: Extra

    open val small: Dp
        @Composable
        @ReadOnlyComposable
        get() = dp.times(Int.single)

    open val medium: Dp
        @Composable
        @ReadOnlyComposable
        get() = dp.times(Int.pair)

    open val large: Dp
        @Composable
        @ReadOnlyComposable
        get() = dp.times(Int.triple)

    data class Extra internal constructor(private val level: Int): Padding(dp) {
        override val extra: Extra
            get() = Extra(level.inc)

        override val small: Dp
            @Composable
            get() = super.small.minus(dp.times(level).half)

        override val medium: Dp
            @Composable
            get() = throw UnsupportedOperationException("$this")

        override val large: Dp
            @Composable
            get() = super.large.plus(dp.times(level).half)
    }

    companion object : Padding(dp = Dp.`8`) {
        override val extra = Extra(Int.`1`)
    }
}
