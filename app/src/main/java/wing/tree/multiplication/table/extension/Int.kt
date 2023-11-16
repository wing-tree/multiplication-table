package wing.tree.multiplication.table.extension

private const val FIVE = 5
private const val FOUR = 4
private const val ONE = 1
private const val THREE = 3
private const val TWO = 2
private const val ZERO = 0

val Int.Companion.five: Int get() = FIVE
val Int.Companion.four: Int get() = FOUR
val Int.Companion.fourthIndex: Int get() = THREE
val Int.Companion.none: Int get() = ZERO
val Int.Companion.one: Int get() = ONE
val Int.Companion.three: Int get() = THREE
val Int.Companion.secondIndex: Int get() = ONE
val Int.Companion.thirdIndex: Int get() = TWO
val Int.Companion.two: Int get() = TWO
val Int.Companion.zero: Int get() = ZERO

val Int.half: Int get() = div(TWO)
val Int.quarter get() = div(FOUR)
val Int.threeQuarters: Int get() = div(Float.threeQuarters).roundedInt

fun Int.incrementByTwo() = plus(Int.two)
fun Int.quadrupled() = times(Int.four)
