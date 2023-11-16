package wing.tree.multiplication.table.extension

private const val EMPTY = ""

val String.Companion.empty: String get() = EMPTY

val String.intOrNull: Int? get() = toIntOrNull()

fun String.fourth() = get(Int.fourthIndex)
fun String.second() = get(Int.secondIndex)
fun String.third() = get(Int.thirdIndex)
