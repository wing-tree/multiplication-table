package wing.tree.multiplication.table.extension

private const val ONE_HUNDRED = 100L
private const val SEVEN = 7L

val Long.Companion.seven: Long get() = SEVEN

val Long.hundreds: Long get() = times(ONE_HUNDRED)
val Long.milliseconds: Long get() = this
