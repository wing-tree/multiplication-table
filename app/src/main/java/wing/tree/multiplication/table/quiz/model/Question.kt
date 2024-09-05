package wing.tree.multiplication.table.quiz.model

import androidx.compose.runtime.mutableStateOf
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.isNotNull
import wing.tree.multiplication.table.extension.function.not

data class Question(
    val timesTable: Int,
    val multiplicand: Int
) {
    val answer = mutableStateOf<Int?>(null)
    val answered: Boolean get() = answer.value.isNotNull()
    val correct: Boolean get() = when {
        unanswered -> false
        else -> product `is` answer.value
    }

    val incorrect: Boolean get() = when {
        unanswered -> false
        else -> product not answer.value
    }

    val product = timesTable.times(multiplicand)
    val unanswered: Boolean get() = answered.not()

    fun clear() {
        answer.value = null
    }
}
