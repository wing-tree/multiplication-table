package wing.tree.multiplication.table.main.view.composable

import android.content.Context.MODE_PRIVATE
import android.content.res.ColorStateList
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.edit
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.RangeSlider.OnSliderTouchListener
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.MultiplicationTableButton
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.dialog.intent.DialogState
import wing.tree.multiplication.table.dialog.model.Dialog
import wing.tree.multiplication.table.extension.function.bounceVertically
import wing.tree.multiplication.table.extension.function.second
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.float
import wing.tree.multiplication.table.extension.property.int
import wing.tree.multiplication.table.main.intent.MainAction
import wing.tree.multiplication.table.model.Key
import wing.tree.multiplication.table.model.Name
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.token.Space
import wing.tree.multiplication.table.top.level.property.MAXIMUM_TIMES_TABLE
import wing.tree.multiplication.table.top.level.property.MINIMUM_TIMES_TABLE
import wing.tree.multiplication.table.top.level.property.fillMaxWidth

@Composable
internal fun Dialog(
    state: DialogState<Dialog.Quiz>,
    onDismissRequest: () -> Unit,
    onAction: (MainAction) -> Unit
) {
    when (state) {
        DialogState.Dismissed -> noOperations()
        is DialogState.Showing -> Dialog(onDismissRequest = onDismissRequest) {
            val value = state.value

            Surface(
                shape = AlertDialogDefaults.shape,
                color = colorScheme.background
            ) {
                Column(
                    modifier = Modifier.padding(Padding.large),
                    verticalArrangement = Arrangement.spacedBy(Space.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val colorScheme = colorScheme
                    val context = LocalContext.current

                    var start by remember {
                        mutableIntStateOf(value.start)
                    }

                    var endInclusive by remember {
                        mutableIntStateOf(value.endInclusive)
                    }

                    Text(
                        text = stringResource(R.string.quiz),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = typography.bodyMedium.merge(fontWeight = FontWeight.Bold)
                    )

                    AndroidView(
                        factory = {
                            RangeSlider(context).apply {
                                val colorStateList = with(colorScheme.primary.toArgb()) {
                                    ColorStateList.valueOf(this)
                                }

                                values = listOf(start, endInclusive).map(Int::float)

                                valueFrom = MINIMUM_TIMES_TABLE.float
                                valueTo = MAXIMUM_TIMES_TABLE.float

                                stepSize = Float.`1`

                                labelBehavior = LabelFormatter.LABEL_VISIBLE

                                thumbTintList = colorStateList
                                trackActiveTintList = colorStateList

                                this.addOnChangeListener { slider, _, _ ->
                                    with(slider.values.map(Float::int)) {
                                        start = first()
                                        endInclusive = second()
                                    }
                                }

                                addOnSliderTouchListener(
                                    object : OnSliderTouchListener {
                                        override fun onStartTrackingTouch(slider: RangeSlider) {
                                            noOperations
                                        }

                                        override fun onStopTrackingTouch(slider: RangeSlider) {
                                            with(slider.values.map(Float::int)) {
                                                val sharedPreferences =
                                                    context.getSharedPreferences(
                                                        Name.QUIZ(),
                                                        MODE_PRIVATE
                                                    )

                                                sharedPreferences.edit {
                                                    putInt(Key.START(), first())
                                                    putInt(Key.END_INCLUSIVE(), second())
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        },
                        update = {
                            it.values = listOf(start, endInclusive).map(Int::float)
                        }
                    )

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
