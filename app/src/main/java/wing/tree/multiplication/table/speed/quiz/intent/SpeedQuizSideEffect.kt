package wing.tree.multiplication.table.speed.quiz.intent

sealed interface SpeedQuizSideEffect {
    data object Home : SpeedQuizSideEffect

    sealed interface Show : SpeedQuizSideEffect {
        data object InterstitialAd : Show
    }
}
