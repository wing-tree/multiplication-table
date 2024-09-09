package wing.tree.multiplication.table.test.intent

sealed interface TestSideEffect {
    data object Home : TestSideEffect

    sealed interface Show : TestSideEffect {
        data object InterstitialAd : Show
    }
}
