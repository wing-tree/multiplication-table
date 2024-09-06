package wing.tree.multiplication.table.speed.quiz.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import wing.tree.multiplication.table.constant.MAXIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MINIMUM_MULTIPLICAND
import wing.tree.multiplication.table.extension.property.firstIndex
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.model.Submission
import kotlin.random.Random

class SpeedQuiz(vararg elements: Question) : PersistentList<Question> by persistentListOf(elements = elements) {
    constructor(start: Int, endInclusive: Int) : this(
        elements = buildList {
            for (timesTable in start..endInclusive) {
                for (multiplicand in MINIMUM_MULTIPLICAND..MAXIMUM_MULTIPLICAND) {
                    add(Question(timesTable, multiplicand))
                }
            }
        }
            .toTypedArray()
    )

    val random: Question get() = random(Random(System.currentTimeMillis())).copy()
    val submission = Submission()

    var index by mutableIntStateOf(Int.firstIndex)

    fun submit(question: Question) {
        if (submission.add(question)) {
            index++
        }
    }
}