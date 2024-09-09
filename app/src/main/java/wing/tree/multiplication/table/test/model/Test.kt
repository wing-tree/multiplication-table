package wing.tree.multiplication.table.test.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.top.level.property.MAXIMUM_MULTIPLICAND
import wing.tree.multiplication.table.top.level.property.MINIMUM_MULTIPLICAND
import wing.tree.multiplication.table.top.level.property.NUMBER_OF_QUESTIONS
import kotlin.random.Random

@Stable
class Test private constructor(
    vararg elements: Question
) : PersistentList<Question> by persistentListOf(elements = elements) {
    constructor(start: Int, endInclusive: Int) : this(
        elements = buildList {
            for (timesTable in start..endInclusive) {
                for (multiplicand in MINIMUM_MULTIPLICAND..MAXIMUM_MULTIPLICAND) {
                    add(timesTable to multiplicand)
                }
            }
        }
            .shuffled(Random(System.currentTimeMillis()))
            .take(NUMBER_OF_QUESTIONS)
            .map { (timesTable, multiplicand) ->
                Question(
                    timesTable = timesTable,
                    multiplicand = multiplicand
                )
            }
            .toTypedArray()
    )
}
