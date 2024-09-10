package wing.tree.multiplication.table.main.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.google.android.material.slider.RangeSlider
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.AnimatedText
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.dialog.intent.DialogState
import wing.tree.multiplication.table.dialog.model.Dialog
import wing.tree.multiplication.table.extension.function.second
import wing.tree.multiplication.table.extension.property.float
import wing.tree.multiplication.table.extension.property.hyphen
import wing.tree.multiplication.table.extension.property.int
import wing.tree.multiplication.table.main.action.MainAction
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.property.MAXIMUM_TIMES_TABLE
import wing.tree.multiplication.table.top.level.property.MINIMUM_TIMES_TABLE

@Composable
internal fun Dialog(
    state: DialogState<Dialog>,
    onDismissRequest: () -> Unit,
    onAction: (MainAction) -> Unit
) {
    when (state) {
        DialogState.Dismissed -> noOperations()
        is DialogState.Showing -> Dialog(onDismissRequest = onDismissRequest) {
            Surface(shape = AlertDialogDefaults.shape) {
                Column(
                    modifier = Modifier.padding(Padding.large),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var start by remember {
                        mutableIntStateOf(MINIMUM_TIMES_TABLE) // TODO save these as persistent.
                    }

                    var endInclusive by remember {
                        mutableIntStateOf(MAXIMUM_TIMES_TABLE)
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedText(text = "$start")
                        Text(text = String.hyphen)
                        AnimatedText(text = "$endInclusive")
                    }

                    val context = LocalContext.current

                    AndroidView(
                        factory = {
                            RangeSlider(context).apply {
                                valueFrom = 2f // TODO extract as const includes belows.
                                valueTo = 17f
                                values = listOf(start, endInclusive).map(Int::float)
                                stepSize = 1f
                                isTickVisible = false

                                addOnChangeListener { slider, _, _ ->
                                    with(slider.values.map(Float::int)) {
                                        start = first()
                                        endInclusive = second()
                                    }
                                }
                            }
                        },
                        update = {
                            it.values = listOf(start, endInclusive).map(Int::float)
                        }
                    )

                    ElevatedButton(
                        onClick = {
                            onAction(
                                MainAction.Navigate.ToSpeedQuiz(
                                    start = start,
                                    endInclusive = endInclusive
                                )
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.speed_quiz)
                        )
                    }

                    ElevatedButton(
                        onClick = {
                            onAction(
                                MainAction.Navigate.ToTest(
                                    start = start,
                                    endInclusive = endInclusive
                                )
                            )
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
