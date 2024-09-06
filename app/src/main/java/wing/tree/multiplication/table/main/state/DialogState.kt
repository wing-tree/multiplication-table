package wing.tree.multiplication.table.main.state

import androidx.compose.runtime.Immutable

@Immutable
sealed interface DialogState {
    data object Dismissed : DialogState
    data object Showing : DialogState
}
