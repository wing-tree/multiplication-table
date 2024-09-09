package wing.tree.multiplication.table.model

import androidx.compose.runtime.Stable

@Stable
sealed interface Action {
    data object Check : Action
    data object SolveAgain : Action

    sealed interface Keyboard : Action {
        data class Next(val index: Int) : Keyboard
    }
}
