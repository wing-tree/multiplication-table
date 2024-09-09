package wing.tree.multiplication.table.dialog.intent

import androidx.compose.runtime.Immutable
import wing.tree.multiplication.table.dialog.model.Dialog

@Immutable
sealed interface DialogState<out T : Dialog> {
    data class Showing<out T : Dialog>(val value: T) : DialogState<T>
    data object Dismissed : DialogState<Nothing>
}
