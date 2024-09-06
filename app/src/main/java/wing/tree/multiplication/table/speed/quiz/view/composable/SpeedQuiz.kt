package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.constant.EQUALS_SIGN
import wing.tree.multiplication.table.extension.empty
import wing.tree.multiplication.table.extension.extraExtraSmall
import wing.tree.multiplication.table.extension.extraLarge
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.intOrNull
import wing.tree.multiplication.table.extension.property.`3`
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.speed.quiz.action.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.model.SpeedQuiz
import wing.tree.multiplication.table.top.level.containerColor

private val regex = Regex("^\\d*\$")

@Composable
internal fun SpeedQuiz(
    speedQuiz: SpeedQuiz,
    onAction: (SpeedQuizAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember {
        FocusRequester()
    }

    val index = speedQuiz.index
    val question = remember(index) {
        speedQuiz.random
    }

    val containerColor by animateColorAsState(
        targetValue = containerColor(question.timesTable),
        label = String.empty
    )

    var answer by question.answer

    LaunchedEffect(key1 = focusRequester) {
        focusRequester.requestFocus()
    }

    Box(modifier = modifier) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(containerColor = containerColor)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(IntrinsicSize.Max)
                    .padding(Dp.extraLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Question(
                    question = question,
                    style = typography.displayMedium.copy(
                        color = colorScheme.onSurface
                    )
                )

                Row(
                    modifier = Modifier.padding(Dp.extraSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val style = typography.displaySmall.copy(
                        color = colorScheme.onSurface
                    )

                    Text(
                        text = EQUALS_SIGN,
                        style = style
                    )

                    BasicTextField(
                        value = "${answer ?: String.empty}",
                        onValueChange = { value ->
                            if (value.matches(regex)) {
                                answer = value.take(Int.`3`).intOrNull
                            }
                        },
                        modifier = Modifier
                            .background(color = colorScheme.surface)
                            .padding(Dp.extraExtraSmall)
                            .focusRequester(focusRequester),
                        textStyle = style,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                onAction(SpeedQuizAction.Next(question))
                            }
                        ),
                        singleLine = true
                    )
                }
            }
        }
    }
}

@Composable
private fun Question(
    question: Question,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    Crossfade(
        targetState = question,
        modifier = modifier,
        label = String.empty
    ) { (timesTable, multiplicand) ->
        Multiplication(
            timesTable = timesTable,
            multiplicand = multiplicand,
            style = style
        )
    }
}
