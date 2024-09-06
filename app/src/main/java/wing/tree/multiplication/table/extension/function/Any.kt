@file:Suppress("unused")

package wing.tree.multiplication.table.extension.function

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

infix fun Any?.`is`(other: Any?) = this == other
infix fun Any?.not(other: Any?) = `is`(other).not()

@OptIn(ExperimentalContracts::class)
fun Any?.isNull(): Boolean {
    contract {
        returns(true) implies (this@isNull == null)
        returns(false) implies (this@isNull != null)
    }

    return this `is` null
}

@OptIn(ExperimentalContracts::class)
fun Any?.isNotNull(): Boolean {
    contract {
        returns(true) implies (this@isNotNull != null)
        returns(false) implies (this@isNotNull == null)
    }

    return this not null
}

fun <T : Any?> T?.`this`(): T? = this
fun <T : Any?> T.ifNull(defaultValue: () -> T) = if (isNull()) {
    defaultValue()
} else {
    this
}
