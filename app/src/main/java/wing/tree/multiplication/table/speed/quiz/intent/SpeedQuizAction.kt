package wing.tree.multiplication.table.speed.quiz.intent

import wing.tree.multiplication.table.model.Question

sealed interface SpeedQuizAction {
    data class Next(val question: Question) : SpeedQuizAction

    data object Home : SpeedQuizAction
    data object OnReady : SpeedQuizAction
    data object SolveNew : SpeedQuizAction
}
