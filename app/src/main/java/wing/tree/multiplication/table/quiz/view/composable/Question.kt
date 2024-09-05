package wing.tree.multiplication.table.quiz.view.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.constant.EQUALS_SIGN
import wing.tree.multiplication.table.constant.MULTIPLICATION_SIGN
import wing.tree.multiplication.table.extension.empty
import wing.tree.multiplication.table.extension.extraExtraSmall
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.full
import wing.tree.multiplication.table.extension.fullyTransparent
import wing.tree.multiplication.table.extension.intOrNull
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.medium
import wing.tree.multiplication.table.extension.function.not
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.quiz.model.Question
import wing.tree.multiplication.table.quiz.state.QuizState
import wing.tree.multiplication.table.theme.pastelGreen
import wing.tree.multiplication.table.theme.pastelRed
import wing.tree.multiplication.table.top.level.containerColor

private const val MAXIMUM_DIGIT_COUNT = 2

private val regex = Regex("^\\d*\$")

@Composable
internal fun Question(
    index: Int,
    question: Question,
    tag: QuizState.Tag,
    focusRequester: FocusRequester,
    onKeyboardAction: (Action.Keyboard) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor(question.timesTable)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dp.medium,
                    vertical = Dp.extraSmall
                )
        ) {
            val checked = tag `is` QuizState.Tag.CHECKED

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var answer by rememberSaveable {
                    question.answer
                }

                val onSurface = colorScheme.onSurface
                val color by animateColorAsState(
                    targetValue = when {
                        tag `is` QuizState.Tag.CLEARING -> onSurface.copy(alpha = Float.fullyTransparent)
                        checked.not() -> onSurface
                        question.correct -> pastelGreen
                        question.incorrect -> pastelRed
                        else -> onSurface
                    },
                    label = String.empty
                )

                Text(question = question)
                BasicTextField(
                    value = "${answer ?: String.empty}",
                    onValueChange = { value ->
                        if (value.matches(regex)) {
                            answer = value.take(MAXIMUM_DIGIT_COUNT).intOrNull
                        }
                    },
                    modifier = Modifier
                        .weight(Float.full)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(Dp.extraExtraSmall)
                        )
                        .focusRequester(focusRequester)
                        .padding(Dp.extraExtraSmall),
                    readOnly = tag not QuizState.Tag.IN_PROGRESS,
                    textStyle = MaterialTheme.typography
                        .bodyLarge
                        .copy(color = color),
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
            }

            val visible = when {
                checked.not() -> false
                else -> question.incorrect
            }

            AnimatedVisibility(
                visible = visible,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dp.extraExtraSmall)
                ) {
                    Text(
                        question = question,
                        modifier = Modifier.alpha(Float.fullyTransparent)
                    )

                    Text(
                        text = "${question.product}",
                        modifier = Modifier.padding(horizontal = Dp.extraExtraSmall),
                        color = Color.Blue,
                        style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.End)
                    )
                }
            }
        }
    }
}

@Composable
private fun Text(
    question: Question,
    modifier: Modifier = Modifier
) {
    with(question) {
        Text(
            text = "$timesTable $MULTIPLICATION_SIGN $multiplicand $EQUALS_SIGN ",
            modifier = modifier,
            color = colorScheme.onSurface
        )
    }
}
