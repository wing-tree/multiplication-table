package wing.tree.multiplication.table.test.intent

sealed interface KeyboardEvent {
    data class Next(val index: Int) : KeyboardEvent
}
