package wing.tree.multiplication.table.extension

import kotlin.math.roundToInt

private const val ONE = 1.00F
private const val ONE_PERCENT = 0.01F
private const val THREE_QUARTERS = 0.75F
private const val ZERO = 0.00F

val Float.Companion.full: Float get() = ONE
val Float.Companion.fullyOpaque: Float get() = ONE
val Float.Companion.fullyTransparent: Float get() = ZERO
val Float.Companion.onePercent: Float get() = ONE_PERCENT
val Float.Companion.threeQuarters: Float get() = THREE_QUARTERS

val Float.negated: Float get() = unaryMinus()
val Float.roundedInt: Int get() = roundToInt()

fun Float.isNotZero(): Boolean = not(ZERO)
