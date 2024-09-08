@file:Suppress("unused")

package wing.tree.multiplication.table.extension.function

import wing.tree.multiplication.table.extension.property.`0`
import wing.tree.multiplication.table.extension.property.`2`
import wing.tree.multiplication.table.model.Percentage
import kotlin.math.pow

fun Float.isNotZero(): Boolean = not(Float.`0`)
fun Float.isZero(): Boolean = `is`(Float.`0`)
fun Float.percent(percentage: Percentage) = times(percentage)
fun Float.square() = pow(Float.`2`)

operator fun Float.minus(other: Percentage) = minus(other())
operator fun Float.plus(other: Percentage) = plus(other())
operator fun Float.times(other: Percentage) = times(other())
