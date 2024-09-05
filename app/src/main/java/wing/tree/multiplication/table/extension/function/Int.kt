@file:Suppress("unused")

package wing.tree.multiplication.table.extension.function

import wing.tree.multiplication.table.extension.property.`2`
import wing.tree.multiplication.table.extension.property.`4`
import wing.tree.multiplication.table.extension.property.double
import wing.tree.multiplication.table.extension.property.float
import wing.tree.multiplication.table.extension.property.int
import wing.tree.multiplication.table.extension.property.isFirstIndex
import wing.tree.multiplication.table.extension.property.isNonNegative
import wing.tree.multiplication.table.extension.property.isZero
import kotlin.math.pow

fun Int.divAsFloat(other: Float): Float = div(other)
fun Int.divAsFloat(other: Int): Float = div(other.float)
fun Int.incrementByTwo() = plus(Int.`2`)
fun Int.pow(n: Int): Int = double.pow(n).int
fun Int.quadrupled() = times(Int.`4`)

inline fun Int.ifIsFirstIndex(then: () -> Unit) {
    if (isFirstIndex) {
        then()
    }
}

inline fun Int.ifIsNonNegative(then: Int.() -> Unit) {
    if (isNonNegative) {
        then(this)
    }
}

inline fun Int.ifIsZero(then: () -> Unit) {
    if (isZero) {
        then()
    }
}
