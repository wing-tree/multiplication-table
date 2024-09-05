@file:Suppress("ObjectPropertyName", "unused")

package wing.tree.multiplication.table.extension.property

val Double.float: Float get() = toFloat()
val Double.int: Int get() = toInt()
val Double.long: Long get() = toLong()

val Double.Companion.`2`: Double get() = 2.00
