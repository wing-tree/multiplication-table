package wing.tree.multiplication.table.test.view.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import wing.tree.multiplication.table.composable.Prompt
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.not
import wing.tree.multiplication.table.extension.function.width
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.empty
import wing.tree.multiplication.table.extension.property.fullyOpaque
import wing.tree.multiplication.table.extension.property.fullyTransparent
import wing.tree.multiplication.table.extension.property.intOrNull
import wing.tree.multiplication.table.extension.property.threeQuarters
import wing.tree.multiplication.table.extension.property.widestDigit
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.test.intent.TestState
import wing.tree.multiplication.table.theme.pastelGreen
import wing.tree.multiplication.table.theme.pastelRed
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.function.containerColor
import wing.tree.multiplication.table.top.level.property.MAXIMUM_DIGITS
import wing.tree.multiplication.table.top.level.property.MAXIMUM_TIMES_TABLE_DIGITS
import wing.tree.multiplication.table.top.level.property.fillMaxWidth
import wing.tree.multiplication.table.top.level.property.regex

@Composable
internal fun Question(
    index: Int,
    question: Question,
    tag: TestState.Tag,
    focusRequester: FocusRequester,
    onKeyboardAction: (Action.Keyboard) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor(question.timesTable)
        )
    ) {
        val textStyle = LocalTextStyle.current.merge(color = colorScheme.onSurface)
        val widestDigitWidth = Char.widestDigit.width(textStyle)

        Column(
            modifier = fillMaxWidth.padding(
                horizontal = Padding.medium,
                vertical = Padding.small
            )
        ) {
            Row(
                modifier = fillMaxWidth,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isClearing = tag `is` TestState.Tag.CLEARING
                val isInCompleted = tag not TestState.Tag.COMPLETED

                val color by animateColorAsState(
                    targetValue = when {
                        isClearing -> colorScheme.surface
                        isInCompleted -> colorScheme.onSurface
                        question.isCorrect -> pastelGreen
                        question.isIncorrect -> pastelRed
                        else -> colorScheme.onSurface
                    },
                    label = String.empty
                )

                var answer by rememberSaveable {
                    question.answer
                }

                Prompt(
                    timesTable = question.timesTable,
                    multiplicand = question.multiplicand,
                    modifier = Modifier.padding(
                        start = widestDigitWidth.times(
                            MAXIMUM_TIMES_TABLE_DIGITS.minus("${question.timesTable}".length)
                        )
                    ),
                    style = textStyle
                )

                Row(
                    modifier = Modifier.weight(Float.`1`),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = "${answer ?: String.empty}",
                        onValueChange = { value ->
                            if (value.matches(regex)) {
                                answer = value.take(MAXIMUM_DIGITS).intOrNull
                            }
                        },
                        modifier = Modifier
                            .weight(weight = Float.`1`)
                            .background(color = colorScheme.surface)
                            .focusRequester(focusRequester)
                            .padding(Padding.extra.small),
                        readOnly = tag not TestState.Tag.IN_PROGRESS,
                        textStyle = textStyle.merge(color = color),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                onKeyboardAction(Action.Keyboard.Next(index))
                            }
                        ),
                        singleLine = true
                    )

                    val alpha by animateFloatAsState(
                        targetValue = when {
                            isInCompleted -> Float.fullyTransparent
                            else -> when {
                                question.isIncorrect -> Float.fullyOpaque
                                else -> Float.fullyTransparent
                            }
                        },
                        label = String.empty
                    )

                    Text(
                        text = "${question.product}",
                        modifier = Modifier
                            .weight(weight = Float.threeQuarters)
                            .padding(horizontal = Padding.extra.small)
                            .graphicsLayer {
                                this.alpha = alpha
                            },
                        color = Color.Blue,
                        style = textStyle.merge(textAlign = TextAlign.End)
                    )
                }
            }
        }
    }
}
