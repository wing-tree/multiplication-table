package wing.tree.multiplication.table.model

import androidx.compose.runtime.mutableStateOf
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.isNotNull
import wing.tree.multiplication.table.extension.function.not

data class Question(
    val timesTable: Int,
    val multiplicand: Int
) {
    val answer = mutableStateOf<Int?>(null)
    val isAnswered: Boolean get() = answer.value.isNotNull()
    val isCorrect: Boolean get() = when {
        isUnanswered -> false
        else -> product `is` answer.value
    }

    val isIncorrect: Boolean get() = when {
        isUnanswered -> false
        else -> product not answer.value
    }

    val isUnanswered: Boolean get() = isAnswered.not()
    val product = timesTable.times(multiplicand)

    fun clear() {
        answer.value = null
    }
}
