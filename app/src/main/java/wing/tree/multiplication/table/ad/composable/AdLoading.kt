package wing.tree.multiplication.table.ad.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.extension.property.oneAndAHalf
import wing.tree.multiplication.table.dialog.intent.DialogState
import wing.tree.multiplication.table.top.level.property.fillMaxSize
import wing.tree.multiplication.table.type.alias.Space

@Composable
internal fun AdLoading(
    state: DialogState,
    onDismissRequest: () -> Unit
) {
    when (state) {
        DialogState.Dismissed -> noOperations()
        else -> androidx.compose.ui.window.Dialog(onDismissRequest = onDismissRequest) {
            Column(
                modifier = fillMaxSize,
                verticalArrangement = Arrangement.spacedBy(
                    space = Space.small.times(Float.oneAndAHalf),
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()

                Text(
                    text = stringResource(R.string.ad_loading),
                    color = colorScheme.inverseOnSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
