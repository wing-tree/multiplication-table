package wing.tree.multiplication.table.test.view.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import kotlinx.collections.immutable.persistentListOf
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.MultiplicationTableButton
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.isLessThan
import wing.tree.multiplication.table.extension.function.isLessThanOrEqualTo
import wing.tree.multiplication.table.extension.function.not
import wing.tree.multiplication.table.extension.function.verticalFadingEdge
import wing.tree.multiplication.table.extension.property.`0`
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.inc
import wing.tree.multiplication.table.extension.property.paddingValues
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.test.intent.TestState
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.token.Space
import wing.tree.multiplication.table.top.level.property.INVALID_INDEX
import wing.tree.multiplication.table.top.level.property.NUMBER_OF_QUESTIONS
import wing.tree.multiplication.table.top.level.property.fillMaxWidth

@Composable
internal fun Test(
    state: TestState,
    widthSizeClass: WindowWidthSizeClass,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    val tag = state.tag
    val test = state.test
    val focusManager = LocalFocusManager.current
    val focusRequesters = remember(state) {
        persistentListOf(
            elements = Array(test.size.inc) {
                FocusRequester()
            }
        )
    }

    val onKeyboardAction: (Action.Keyboard) -> Unit = remember(state) {
        { keyboardAction ->
            when (keyboardAction) {
                is Action.Keyboard.Next -> {
                    if (test.count(Question::isAnswered) isLessThan NUMBER_OF_QUESTIONS) {
                        var index = test.withIndex().indexOfFirst { (index, value) ->
                            when {
                                index isLessThanOrEqualTo keyboardAction.index -> false
                                else -> value.isUnanswered
                            }
                        }

                        if (index `is` INVALID_INDEX) {
                            index = test.indexOfFirst(Question::isUnanswered)
                        }

                        if (index not INVALID_INDEX) {
                            focusRequesters[index].requestFocus()
                        }
                    } else {
                        focusRequesters.last().requestFocus()
                    }
                }
            }
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.padding(vertical = Padding.medium),
        verticalArrangement = Arrangement.spacedBy(space = Space.small)
    ) {
        val isInProgress = tag.isInProgress
        val interactionSource = remember {
            MutableInteractionSource()
        }

        LaunchedEffect(isInProgress) {
            if (isInProgress) {
                scrollState.animateScrollTo(value = Int.`0`)

                focusRequesters.first().requestFocus()
            }
        }

        AnimatedVisibility(
            visible = tag `is` TestState.Tag.COMPLETED,
            modifier = fillMaxWidth
        ) {
            Score(
                state = state,
                modifier = fillMaxWidth
            )
        }

        Column(
            modifier = fillMaxWidth
                .weight(weight = Float.`1`)
                .verticalScroll(state = scrollState)
                .verticalFadingEdge(scrollState = scrollState, length = Padding.extra.small)
                .padding(vertical = Padding.small),
            verticalArrangement = Arrangement.spacedBy(space = Space.small)
        ) {
            test.forEachIndexed { index, question ->
                Question(
                    index = index,
                    question = question,
                    tag = tag,
                    focusRequester = focusRequesters[index],
                    onKeyboardAction = onKeyboardAction,
                    modifier = fillMaxWidth.padding(widthSizeClass.paddingValues)
                )
            }
        }

        val visible = when {
            state is TestState.Preparing -> false
            tag `is` TestState.Tag.CLEARING -> false
            else -> state.allAnswered
        }

        LaunchedEffect(visible) {
            if (visible) {
                focusManager.clearFocus()
            }
        }

        Column(
            modifier = fillMaxWidth.padding(vertical = Padding.small)
        ) {
            val style = typography.bodyMedium.merge(fontWeight = FontWeight.Bold)

            AnimatedVisibility(
                visible = isInProgress.not()
            ) {
                Column {
                    MultiplicationTableButton(
                        onClick = {

                        }
                    ) {
                        Text(
                            text = stringResource(R.string.home),
                            modifier = Modifier.align(Alignment.Center),
                            style = style
                        )
                    }

                    MultiplicationTableButton(
                        onClick = {

                        }
                    ) {
                        Text(
                            text = stringResource(R.string.home),
                            modifier = Modifier.align(Alignment.Center),
                            style = style
                        )
                    }
                }
            }

            AnimatedVisibility(visible = visible) {
                MultiplicationTableButton(
                    onClick = {
                        val action = when (state) {
                            is TestState.Completed -> Action.SolveAgain
                            else -> {
                                focusManager.clearFocus()

                                Action.Check
                            }
                        }

                        onAction(action)
                    },
                    modifier = Modifier
                        .focusRequester(focusRequesters.last())
                        .focusable(interactionSource = interactionSource)
                ) {
                    Text(
                        text = when {
                            isInProgress -> stringResource(id = R.string.check)
                            else -> stringResource(id = R.string.solve_again)
                        },
                        modifier = Modifier.align(Alignment.Center),
                        style = style
                    )
                }
            }
        }
    }
}
