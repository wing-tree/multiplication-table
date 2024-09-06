package wing.tree.multiplication.table.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import wing.tree.multiplication.table.constant.MAXIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MINIMUM_MULTIPLICAND
import wing.tree.multiplication.table.extension.property.firstIndex

class Quiz private constructor(vararg elements: Question) : PersistentList<Question> by persistentListOf(elements = elements) {
    private var index by mutableIntStateOf(Int.firstIndex)

    val current: Question get() = get(index)

    constructor(start: Int, endInclusive: Int) : this(
        elements = buildList {
            for (timesTable in start..endInclusive) {
                for (multiplicand in MINIMUM_MULTIPLICAND..MAXIMUM_MULTIPLICAND) {
                    add(Question(timesTable, multiplicand))
                }
            }
        }
            .shuffled()
            .toTypedArray()
    )
}
