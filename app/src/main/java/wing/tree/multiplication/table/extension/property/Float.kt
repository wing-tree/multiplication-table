@file:Suppress("ObjectPropertyName", "unused")

package wing.tree.multiplication.table.extension.property

import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.not
import kotlin.math.PI
import kotlin.math.roundToInt

val Float.complement: Float get() = run {
    if (`is`(Float.NaN)) {
        return Float.`0`
    }

    val other = coerceIn(
        minimumValue = Float.`0`,
        maximumValue = Float.`1`
    )

    Float.`1`.minus(other)
}

val Float.half: Float get() = div(Float.`2`)
val Float.int: Int get() = toInt()
val Float.isNotZero: Boolean get() = not(Float.`0`)
val Float.long: Long get() = toLong()
val Float.negated: Float get() = unaryMinus()
val Float.quarter: Float get() = div(Float.`4`)
val Float.radians: Double get() = div(Float.straightAngle).times(PI)
val Float.roundedInt: Int get() = roundToInt()
val Float.self: Float get() = this
val Float.threeQuarters: Float get() = times(Float.threeQuarters)
val Float.twice: Float get() = times(Float.`2`)

val Float.Companion.`0`: Float get() = 0.00F
val Float.Companion.`1`: Float get() = 1.00F
val Float.Companion.`2`: Float get() = 2.00F
val Float.Companion.`3`: Float get() = 3.00F
val Float.Companion.`4`: Float get() = 4.00F
val Float.Companion.`5`: Float get() = 5.00F
val Float.Companion.`6`: Float get() = 6.00F
val Float.Companion.`7`: Float get() = 7.00F
val Float.Companion.`8`: Float get() = 8.00F
val Float.Companion.`9`: Float get() = 9.00F
val Float.Companion.double: Float get() = 2.00F
val Float.Companion.fourFifths: Float get() = 0.80F
val Float.Companion.fullCircle: Float get() = 360.00F
val Float.Companion.fullyOpaque: Float get() = Float.`1`
val Float.Companion.fullyTransparent: Float get() = Float.`0`
val Float.Companion.half: Float get() = 0.50F
val Float.Companion.none: Float get() = Float.`0`
val Float.Companion.oneAndAQuarter: Float get() = 1.25F
val Float.Companion.oneEighth: Float get() = 0.125F
val Float.Companion.onePercent: Float get() = 0.01F
val Float.Companion.quarter: Float get() = 0.25F
val Float.Companion.rightAngle: Float get() = 90.00F
val Float.Companion.straightAngle: Float get() = 180.00F
val Float.Companion.threeQuarters: Float get() = 0.75F
