package wing.tree.multiplication.table.speed.quiz.side.effect

sealed interface SpeedQuizSideEffect {
    data object Home : SpeedQuizSideEffect

    sealed interface Show : SpeedQuizSideEffect {
        data object InterstitialAd : Show
    }
}
