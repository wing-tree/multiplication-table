package wing.tree.multiplication.table.test.intent

sealed interface TestSideEffect {
    sealed interface Navigate {
        data object ToHome : TestSideEffect
    }

    sealed interface Show : TestSideEffect {
        data object InterstitialAd : Show
    }
}
