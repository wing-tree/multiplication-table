package wing.tree.multiplication.table.ad.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.dialog.intent.DialogState
import wing.tree.multiplication.table.dialog.model.Dialog
import wing.tree.multiplication.table.token.Space
import wing.tree.multiplication.table.top.level.property.fillMaxSize

@Composable
internal fun ProgressDialog(
    state: DialogState<Dialog.Progress>,
    onDismissRequest: () -> Unit
) {
    when (state) {
        DialogState.Dismissed -> noOperations()
        is DialogState.Showing<Dialog.Progress> -> Dialog(onDismissRequest = onDismissRequest) {
            Column(
                modifier = fillMaxSize,
                verticalArrangement = Arrangement.spacedBy(
                    space = Space.extra.small,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()

                state.value.message?.let {
                    Text(
                        text = it,
                        color = colorScheme.inverseOnSurface,
                        style = typography.bodyMedium.merge(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
