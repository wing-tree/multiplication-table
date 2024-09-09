package wing.tree.multiplication.table.main.view.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.dialog.intent.DialogState
import wing.tree.multiplication.table.dialog.model.Dialog
import wing.tree.multiplication.table.extension.`28`
import wing.tree.multiplication.table.extension.`280`
import wing.tree.multiplication.table.extension.`560`
import wing.tree.multiplication.table.main.action.MainAction

@Composable
internal fun Dialog(
    state: DialogState<Dialog>,
    onDismissRequest: () -> Unit,
    onAction: (MainAction) -> Unit
) {
    when (state) {
        DialogState.Dismissed -> noOperations()
        else -> Dialog(onDismissRequest = onDismissRequest) {
            Surface(
                modifier = Modifier.heightIn(
                    min = Dp.`280`,
                    max = Dp.`560`
                ),
                shape = RoundedCornerShape(size = Dp.`28`)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ElevatedButton(
                        onClick = {
                            onAction(MainAction.Navigate.ToSpeedQuiz)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.speed_quiz)
                        )
                    }

                    ElevatedButton(
                        onClick = {
                            onAction(MainAction.Navigate.ToTest)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.test)
                        )
                    }
                }
            }
        }
    }
}
