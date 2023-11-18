@file:Suppress("unused")

package wing.tree.multiplication.table.extension

infix fun <T : Comparable<T>> T.isGreaterThan(other: T): Boolean = this > other
infix fun <T : Comparable<T>> T.isGreaterThanOrEqualTo(other: T): Boolean = this >= other
infix fun <T : Comparable<T>> T.isLessThan(other: T): Boolean = this < other
infix fun <T : Comparable<T>> T.isLessThanOrEqualTo(other: T): Boolean = this <= other
