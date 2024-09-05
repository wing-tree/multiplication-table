@file:Suppress("ObjectPropertyName", "unused")

package wing.tree.multiplication.table.extension.property

import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.isGreaterThan
import wing.tree.multiplication.table.extension.function.isGreaterThanOrEqualTo
import wing.tree.multiplication.table.extension.function.isLessThan
import wing.tree.multiplication.table.extension.function.not
import wing.tree.multiplication.table.extension.function.pow
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.toDuration

val Int.dec: Int get() = dec()
val Int.digit: Int get() = "$this".length
val Int.double: Double get() = toDouble()
val Int.float: Float get() = toFloat()
val Int.half: Int get() = div(Int.`2`)
val Int.halfAsFloat: Float get() = float.half
val Int.hundreds: Int get() = times(Int.oneHundred)
val Int.identity: Int get() = this
val Int.inc: Int get() = inc()
val Int.isEven: Boolean get() = rem(Int.`2`) `is` Int.`0`
val Int.isFirstIndex: Boolean get() = `is`(Int.firstIndex)
val Int.isNegative: Boolean get() = isLessThan(Int.`0`)
val Int.isNonNegative: Boolean get() = isGreaterThanOrEqualTo(Int.`0`)
val Int.isNotOne: Boolean get() = not(Int.`1`)
val Int.isOdd: Boolean get() = isEven.not()
val Int.isOne: Boolean get() = `is`(Int.`1`)
val Int.isPositive: Boolean get() = isGreaterThan(Int.`0`)
val Int.isSecondIndex: Boolean get() = `is`(Int.secondIndex)
val Int.isZero: Boolean get() = `is`(Int.`0`)
val Int.long: Long get() = toLong()
val Int.negated: Int get() = unaryMinus()
val Int.ordinal: String
    get() = run {
        val suffix = when (rem(Int.oneHundred)) {
            Int.`11`, Int.`12`, Int.`13` -> Suffix.TH
            else -> when (rem(Int.ten)) {
                Int.`1` -> Suffix.ST
                Int.`2` -> Suffix.ND
                Int.`3` -> Suffix.RD
                else -> Suffix.TH
            }
        }

        return "$this$suffix"
    }

val Int.quarter get() = div(Int.`4`)
val Int.randomSign: Int
    get() = if (Random.nextBoolean()) {
        absoluteValue
    } else {
        negated
    }

val Int.reciprocal: Float get() = Float.`1`.div(this)
val Int.sqrt: Int get() = sqrt(double).int
val Int.square: Int get() = pow(Int.`2`)
val Int.threeQuarters: Int get() = float.threeQuarters.roundToInt()
val Int.twice: Int get() = times(Int.`2`)

val Int.Companion.`0`: Int get() = 0
val Int.Companion.`1`: Int get() = 1
val Int.Companion.`11`: Int get() = 11
val Int.Companion.`12`: Int get() = 12
val Int.Companion.`13`: Int get() = 13
val Int.Companion.`2`: Int get() = 2
val Int.Companion.`3`: Int get() = 3
val Int.Companion.`4`: Int get() = 4
val Int.Companion.`5`: Int get() = 5
val Int.Companion.`6`: Int get() = 6
val Int.Companion.`7`: Int get() = 7
val Int.Companion.`8`: Int get() = 8
val Int.Companion.`9`: Int get() = 9
val Int.Companion.`-1`: Int get() = -1
val Int.Companion.firstIndex: Int get() = Int.`0`
val Int.Companion.none: Int get() = Int.`0`
val Int.Companion.oneHundred: Int get() = 100
val Int.Companion.oneMinuteInMilliseconds: Int
    get() = `1`.toDuration(DurationUnit.MINUTES)
        .inWholeMilliseconds
        .int

val Int.Companion.oneSecondInMilliseconds: Int
    get() = `1`.toDuration(DurationUnit.SECONDS)
        .inWholeMilliseconds
        .int

val Int.Companion.oneThousand: Int get() = 1000
val Int.Companion.pair: Int get() = Int.`2`
val Int.Companion.secondIndex: Int get() = Int.`1`
val Int.Companion.single: Int get() = Int.`1`
val Int.Companion.ten: Int get() = 10
val Int.Companion.triple: Int get() = Int.`3`

private enum class Suffix {
    ND, RD, ST, TH;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}
