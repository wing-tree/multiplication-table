package wing.tree.multiplication.table.speed.quiz.intent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import wing.tree.multiplication.table.extension.property.`0`
import wing.tree.multiplication.table.speed.quiz.model.SpeedQuiz
import wing.tree.multiplication.table.speed.quiz.view.model.SpeedQuizViewModel

sealed interface SpeedQuizState {
    val elapsedTime: Long

    sealed interface Play : SpeedQuizState {
        val speedQuiz: SpeedQuiz

        data class Played(override val speedQuiz: SpeedQuiz) : Play {
            override val elapsedTime: Long = SpeedQuizViewModel.millisecondsInFuture
        }

        data class Playing(override val speedQuiz: SpeedQuiz) : Play {
            override var elapsedTime by mutableLongStateOf(Long.`0`)

            fun finish() = Played(speedQuiz = speedQuiz)
        }
    }

    data object Preparing : SpeedQuizState {
        override val elapsedTime = Long.`0`
    }
}
