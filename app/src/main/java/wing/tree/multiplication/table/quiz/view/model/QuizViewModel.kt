package wing.tree.multiplication.table.quiz.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import wing.tree.multiplication.table.extension.property.`7`
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.extension.property.immutableList
import wing.tree.multiplication.table.quiz.QuizGenerator
import wing.tree.multiplication.table.quiz.model.Question
import wing.tree.multiplication.table.quiz.state.QuizState
import kotlin.time.Duration.Companion.milliseconds

class QuizViewModel : ViewModel() {
    private val quiz = QuizGenerator.run().immutableList
    private val _quizState = MutableStateFlow<QuizState>(QuizState.InProgress(quiz))

    val state = _quizState.asStateFlow()

    var solvedAtLeastOnce = false
        private set

    fun check() {
        _quizState.update {
            when (it) {
                is QuizState.InProgress -> if (it.allAnswered) {
                    solvedAtLeastOnce = true

                    QuizState.Checked(it.quiz)
                } else {
                    it
                }

                else -> it
            }
        }
    }

    fun solveAgain() {
        viewModelScope.launch {
            _quizState.value = QuizState.Clearing(quiz)

            delay(Long.`7`.hundreds.milliseconds)

            _quizState.update {
                QuizState.InProgress(it.quiz.onEach(Question::clear))
            }
        }
    }
}
