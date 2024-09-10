package wing.tree.multiplication.table.main.view.composable

import android.content.res.ColorStateList
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.google.android.material.slider.RangeSlider
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.AnimatedText
import wing.tree.multiplication.table.composable.MultiplicationTableButton
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.dialog.intent.DialogState
import wing.tree.multiplication.table.dialog.model.Dialog
import wing.tree.multiplication.table.extension.function.bounceVertically
import wing.tree.multiplication.table.extension.function.second
import wing.tree.multiplication.table.extension.property.float
import wing.tree.multiplication.table.extension.property.hyphen
import wing.tree.multiplication.table.extension.property.int
import wing.tree.multiplication.table.main.intent.MainAction
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.token.Space
import wing.tree.multiplication.table.top.level.property.MAXIMUM_TIMES_TABLE
import wing.tree.multiplication.table.top.level.property.MINIMUM_TIMES_TABLE
import wing.tree.multiplication.table.top.level.property.fillMaxWidth

@Composable
internal fun Dialog(
    state: DialogState<Dialog>,
    onDismissRequest: () -> Unit,
    onAction: (MainAction) -> Unit
) {
    when (state) {
        DialogState.Dismissed -> noOperations()
        is DialogState.Showing -> Dialog(onDismissRequest = onDismissRequest) {
            Surface(
                shape = AlertDialogDefaults.shape,
                color = colorScheme.background
            ) {
                Column(
                    modifier = Modifier.padding(Padding.large),
                    verticalArrangement = Arrangement.spacedBy(space = Space.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var start by remember {
                        mutableIntStateOf(MINIMUM_TIMES_TABLE) // TODO save these as persistent.
                    }

                    var endInclusive by remember {
                        mutableIntStateOf(MAXIMUM_TIMES_TABLE)
                    }

                    Column(
                        modifier = fillMaxWidth,
                        verticalArrangement = Arrangement.spacedBy(space = Space.small),
                    ) {
                        val colorScheme = colorScheme
                        val context = LocalContext.current

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimatedText(text = "$start")
                            Text(text = String.hyphen)
                            AnimatedText(text = "$endInclusive")
                        }

                        AndroidView(
                            factory = {
                                RangeSlider(context).apply {
                                    val colorStateList = with(colorScheme.primary.toArgb()) {
                                        ColorStateList.valueOf(this)
                                    }

                                    thumbTintList = colorStateList
                                    trackActiveTintList = colorStateList

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
                    }

                    Column(
                        modifier = fillMaxWidth.bounceVertically(
                            targetValue = Padding.extra.small
                        ),
                        verticalArrangement = Arrangement.spacedBy(space = Space.small)
                    ) {
                        MultiplicationTableButton(
                            onClick = {
                                onAction(
                                    MainAction.Navigate.ToSpeedQuiz(
                                        start = start,
                                        endInclusive = endInclusive
                                    )
                                )
                            },
                            containerColor = colorScheme.secondary,
                            contentColor = colorScheme.onSurfaceVariant

                        ) {
                            Text(
                                text = stringResource(R.string.speed_quiz),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        MultiplicationTableButton(
                            onClick = {
                                onAction(
                                    MainAction.Navigate.ToTest(
                                        start = start,
                                        endInclusive = endInclusive
                                    )
                                )
                            },
                            containerColor = colorScheme.tertiary,
                            contentColor = colorScheme.onSurfaceVariant
                        ) {
                            Text(
                                text = stringResource(R.string.test),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}
