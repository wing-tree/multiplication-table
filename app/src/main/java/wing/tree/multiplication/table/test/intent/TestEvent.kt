package wing.tree.multiplication.table.test.intent

sealed interface TestEvent {
    data object Prepared : TestEvent

    sealed interface Click : TestEvent {
        data object Check : Click
        data object Home : Click
        data object SolveAgain : Click
        data object SolveNew : Click
    }
}
