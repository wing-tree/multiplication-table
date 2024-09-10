package wing.tree.multiplication.table.test.intent

sealed interface TestEvent {
    sealed interface Click : TestEvent {
        data object Check : Click
        data object Home : Click
        data object SolveAgain : Click
    }
}
