package wing.tree.multiplication.table.dialog.intent

import androidx.compose.runtime.Immutable

@Immutable
sealed interface DialogState {
    data object Dismissed : DialogState
    data object Showing : DialogState
}
