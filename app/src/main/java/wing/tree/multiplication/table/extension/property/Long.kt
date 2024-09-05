@file:Suppress("ObjectPropertyName")

package wing.tree.multiplication.table.extension.property

val Long.hundreds: Long get() = times(Long.oneHundred)
val Long.milliseconds: Long get() = this

val Long.Companion.`7`: Long get() = 7L
val Long.Companion.oneHundred: Long get() = 100L
