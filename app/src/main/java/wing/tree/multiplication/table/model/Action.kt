package wing.tree.multiplication.table.model

sealed interface Action {
    data object Check : Action
    data object RateReview : Action
    data object Quiz : Action
    data object Share : Action
    data object SolveAgain : Action
    data object Store : Action

    sealed interface Keyboard : Action {
        data class Next(val index: Int) : Keyboard
    }
}
