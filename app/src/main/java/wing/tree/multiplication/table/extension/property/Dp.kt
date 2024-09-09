@file:Suppress("ObjectPropertyName", "unused")

package wing.tree.multiplication.table.extension.property

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.not

val Dp.Companion.`0`: Dp get() = 0.00.dp
val Dp.Companion.`1`: Dp get() = 1.00.dp
val Dp.Companion.`12`: Dp get() = 12.00.dp
val Dp.Companion.`16`: Dp get() = 16.00.dp
val Dp.Companion.`18`: Dp get() = 18.00.dp
val Dp.Companion.`2`: Dp get() = 2.00.dp
val Dp.Companion.`20`: Dp get() = 20.00.dp
val Dp.Companion.`24`: Dp get() = 24.00.dp
val Dp.Companion.`28`: Dp get() = 28.00.dp
val Dp.Companion.`280`: Dp get() = 280.00.dp
val Dp.Companion.`3`: Dp get() = 3.00.dp
val Dp.Companion.`32`: Dp get() = 32.00.dp
val Dp.Companion.`36`: Dp get() = 36.00.dp
val Dp.Companion.`4`: Dp get() = 4.00.dp
val Dp.Companion.`40`: Dp get() = 40.00.dp
val Dp.Companion.`42`: Dp get() = 42.00.dp
val Dp.Companion.`48`: Dp get() = 48.00.dp
val Dp.Companion.`5`: Dp get() = 5.00.dp
val Dp.Companion.`56`: Dp get() = 56.00.dp
val Dp.Companion.`560`: Dp get() = 560.00.dp
val Dp.Companion.`6`: Dp get() = 6.00.dp
val Dp.Companion.`64`: Dp get() = 64.00.dp
val Dp.Companion.`7`: Dp get() = 7.00.dp
val Dp.Companion.`8`: Dp get() = 8.00.dp
val Dp.Companion.`80`: Dp get() = 80.00.dp
val Dp.Companion.`9`: Dp get() = 9.00.dp
val Dp.Companion.`-1`: Dp get() = `1`.negated
val Dp.Companion.none: Dp get() = 0.00.dp

val Dp.half: Dp get() = div(Float.`2`)
val Dp.isNotZero: Boolean get() = not(Dp.`0`)
val Dp.isZero: Boolean get() = `is`(Dp.`0`)
val Dp.negated: Dp get() = unaryMinus()
val Dp.quarter: Dp get() = div(Float.`4`)
val Dp.triple: Dp get() = times(Float.`3`)
val Dp.threeQuarters: Dp get() = times(Float.threeQuarters)
val Dp.twice: Dp get() = times(Float.`2`)
