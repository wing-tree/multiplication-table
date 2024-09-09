package wing.tree.multiplication.table.token

import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.extension.`4`
import wing.tree.multiplication.table.extension.`8`
import wing.tree.multiplication.table.extension.half
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.inc
import wing.tree.multiplication.table.extension.triple
import wing.tree.multiplication.table.extension.twice

internal abstract class Padding(val dp: Dp) {
    abstract val extra: Extra

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

    internal object Compact : Padding(Dp.`4`) {
        override val extra = Extra(dp = dp, level = Int.`1`)
    }

    companion object : Padding(Dp.`8`) {
        override val extra = Extra(dp = dp, level = Int.`1`)
    }
}
