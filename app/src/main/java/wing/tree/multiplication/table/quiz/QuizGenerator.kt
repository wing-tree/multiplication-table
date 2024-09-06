package wing.tree.multiplication.table.quiz

import wing.tree.multiplication.table.constant.MAXIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MAXIMUM_TIMES_TABLE
import wing.tree.multiplication.table.constant.MINIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MINIMUM_TIMES_TABLE
import wing.tree.multiplication.table.constant.NUMBER_OF_QUESTIONS
import wing.tree.multiplication.table.model.Question
import kotlin.random.Random

object QuizGenerator {
    private val multiplicationTable = buildList {
        for (timesTable in MINIMUM_TIMES_TABLE..MAXIMUM_TIMES_TABLE) {
            for (multiplicand in MINIMUM_MULTIPLICAND..MAXIMUM_MULTIPLICAND) {
                add(timesTable to multiplicand)
            }
        }
    }

    private val random: Random get() = Random(System.currentTimeMillis())

    fun run() = multiplicationTable
        .shuffled(random)
        .take(NUMBER_OF_QUESTIONS)
        .map { (timesTable, multiplicand) ->
            Question(
                timesTable = timesTable,
                multiplicand = multiplicand
            )
        }
}
