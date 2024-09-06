package wing.tree.multiplication.table.speed.quiz.action

import wing.tree.multiplication.table.model.Question

sealed interface SpeedQuizAction {
    data class Next(val question: Question) : SpeedQuizAction

    data object Home : SpeedQuizAction
    data object OnReady : SpeedQuizAction
    data object Replay : SpeedQuizAction
}
