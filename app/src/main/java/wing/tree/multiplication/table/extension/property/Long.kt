@file:Suppress("ObjectPropertyName", "unused")

package wing.tree.multiplication.table.extension.property

import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.isGreaterThan
import wing.tree.multiplication.table.extension.function.isGreaterThanOrEqualTo
import wing.tree.multiplication.table.extension.function.isLessThan
import wing.tree.multiplication.table.extension.function.isLessThanOrEqualTo
import kotlin.math.roundToLong
import kotlin.time.DurationUnit
import kotlin.time.toDuration

val Long.float: Float get() = toFloat()
val Long.half: Long get() = div(Long.`2`)
val Long.hundreds: Long get() = times(Long.oneHundred)
val Long.int: Int get() = toInt()
val Long.isNegative: Boolean get() = isLessThan(Long.`0`)
val Long.isNonNegative: Boolean get() = isGreaterThanOrEqualTo(Long.`0`)
val Long.isNonPositive: Boolean get() = isLessThanOrEqualTo(Long.`0`)
val Long.isPositive: Boolean get() = isGreaterThan(Long.`0`)
val Long.isZero: Boolean get() = `is`(Long.`0`)
val Long.quarter: Long get() = float.quarter.roundToLong()
val Long.threeQuarters: Long get() = float.threeQuarters.roundToLong()
val Long.twice: Long get() = times(Int.`2`)

val Long.Companion.`0`: Long get() = 0L
val Long.Companion.`1`: Long get() = 1L
val Long.Companion.`2`: Long get() = 2L
val Long.Companion.`3`: Long get() = 3L
val Long.Companion.`30`: Long get() = 30L
val Long.Companion.`4`: Long get() = 4L
val Long.Companion.`5`: Long get() = 5L
val Long.Companion.`7`: Long get() = 7L
val Long.Companion.`-1`: Long get() = -1L
val Long.Companion.fiveSecondsInMilliseconds: Long get() = `5`.toDuration(DurationUnit.SECONDS).inWholeMilliseconds
val Long.Companion.fourSecondsInMilliseconds: Long get() = `4`.toDuration(DurationUnit.SECONDS).inWholeMilliseconds
val Long.Companion.oneHourInMilliseconds: Long get() = `1`.toDuration(DurationUnit.HOURS).inWholeMilliseconds
val Long.Companion.oneHundred: Long get() = 100L
val Long.Companion.oneMinuteInMilliseconds: Long get() = `1`.toDuration(DurationUnit.MINUTES).inWholeMilliseconds
val Long.Companion.oneMinuteInSeconds: Long get() = `1`.toDuration(DurationUnit.MINUTES).inWholeSeconds
val Long.Companion.oneSecondInMilliseconds: Long get() = oneThousand
val Long.Companion.oneThousand: Long get() = 1000L
val Long.Companion.none: Long get() = `0`
val Long.Companion.thirtySecondsInMilliseconds: Long get() = `30`.toDuration(DurationUnit.SECONDS).inWholeMilliseconds
val Long.Companion.threeMinutesInMilliseconds: Long get() = `3`.toDuration(DurationUnit.MINUTES).inWholeMilliseconds
val Long.Companion.threeSecondsInMilliseconds: Long get() = `3`.toDuration(DurationUnit.SECONDS).inWholeMilliseconds
val Long.Companion.twoSecondsInMilliseconds: Long get() = oneThousand.times(Long.`2`)
