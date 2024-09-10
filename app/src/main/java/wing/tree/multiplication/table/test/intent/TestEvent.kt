package wing.tree.multiplication.table.test.intent

sealed interface TestEvent {
    data object Check : TestEvent
    data object SolveAgain : TestEvent

    sealed interface Keyboard : TestEvent {
        data class Next(val index: Int) : Keyboard
    }
}
