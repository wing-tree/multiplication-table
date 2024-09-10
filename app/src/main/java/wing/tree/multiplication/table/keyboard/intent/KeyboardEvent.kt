package wing.tree.multiplication.table.keyboard.intent

sealed interface KeyboardEvent {
    data class Next(val index: Int) : KeyboardEvent
}
