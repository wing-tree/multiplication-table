package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
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
import wing.tree.multiplication.table.composable.Prompt
import wing.tree.multiplication.table.extension.property.`0`
import wing.tree.multiplication.table.extension.property.`3`
import wing.tree.multiplication.table.extension.property.empty
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.extension.property.intOrNull
import wing.tree.multiplication.table.extension.property.isZero
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizState
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.function.containerColor
import wing.tree.multiplication.table.top.level.property.MAXIMUM_DIGITS
import wing.tree.multiplication.table.top.level.property.fillMaxWidth
import wing.tree.multiplication.table.top.level.property.regex

@Composable
internal fun Playing(
    state: SpeedQuizState.Play.Playing,
    onAction: (SpeedQuizAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember {
        FocusRequester()
    }

    val speedQuiz = state.speedQuiz
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
        Card(
            modifier = fillMaxWidth,
            colors = CardDefaults.cardColors(containerColor = containerColor)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(Padding.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val style = typography.headlineLarge.copy(
                    color = colorScheme.onSurface
                )

                Question(
                    index = index,
                    question = question,
                    modifier = fillMaxWidth,
                    style = style
                )

                Row(
                    modifier = Modifier.padding(Padding.small),
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
                            .background(color = colorScheme.surface)
                            .padding(Padding.extra.small)
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
    index: Int,
    question: Question,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    AnimatedContent(
        targetState = IndexedValue(index = index, value = question),
        modifier = modifier,
        transitionSpec = {
            val delayMillis = when {
                targetState.index.isZero -> Int.`0`
                else -> Int.`3`.hundreds
            }

            fadeIn(tween(delayMillis = delayMillis)) togetherWith fadeOut()
        },
        contentAlignment = Alignment.Center,
        label = String.empty
    ) { (_, value) ->
        val (timesTable, multiplicand) = value

        Prompt(
            timesTable = timesTable,
            multiplicand = multiplicand,
            modifier = Modifier.wrapContentSize(),
            style = style
        )
    }
}
