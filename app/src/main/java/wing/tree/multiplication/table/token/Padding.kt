package wing.tree.multiplication.table.token

import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`4`
import wing.tree.multiplication.table.extension.property.`8`
import wing.tree.multiplication.table.extension.property.half
import wing.tree.multiplication.table.extension.property.inc
import wing.tree.multiplication.table.extension.property.triple
import wing.tree.multiplication.table.extension.property.twice

internal abstract class Padding(val dp: Dp) {
    open val extra: Extra get() = Extra(dp = dp, level = Int.`1`)
    open val small: Dp = dp
    open val medium: Dp = dp.twice
    open val large: Dp = dp.triple

    class Extra internal constructor(dp: Dp, val level: Int) : Padding(dp) {
        override val extra: Extra get() = Extra(dp, level.inc)

        override val small: Dp
            get() = super.small.minus(dp.half.times(level))

        override val medium: Dp
            get() = throw UnsupportedOperationException("$this")

        override val large: Dp
            get() = super.large.plus(dp.half.times(level))
    }

    internal object Compact : Padding(Dp.`4`)

    companion object : Padding(Dp.`8`)
}
