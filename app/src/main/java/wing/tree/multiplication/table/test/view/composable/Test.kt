package wing.tree.multiplication.table.test.view.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import wing.tree.multiplication.table.composable.Icon
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.isLessThan
import wing.tree.multiplication.table.extension.function.isLessThanOrEqualTo
import wing.tree.multiplication.table.extension.function.not
import wing.tree.multiplication.table.extension.function.verticalFadingEdge
import wing.tree.multiplication.table.extension.property.empty
import wing.tree.multiplication.table.extension.property.inc
import wing.tree.multiplication.table.extension.property.paddingValues
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.test.state.TestState
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.property.INVALID_INDEX
import wing.tree.multiplication.table.top.level.property.NUMBER_OF_QUESTIONS
import wing.tree.multiplication.table.top.level.property.fillMaxWidth
import wing.tree.multiplication.table.type.alias.Space

@Composable
internal fun Test(
    state: TestState,
    widthSizeClass: WindowWidthSizeClass,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
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

    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .verticalFadingEdge(scrollState)
            .padding(vertical = Padding.small)
            .padding(paddingValues = widthSizeClass.paddingValues),
        verticalArrangement = Arrangement.spacedBy(space = Space.small)
    ) {
        AnimatedContent(
            targetState = state is TestState.Completed,
            modifier = fillMaxWidth,
            transitionSpec = {
                val enter = expandVertically().plus(fadeIn())
                val exit = fadeOut().plus(shrinkVertically())

                enter togetherWith exit
            },
            label = String.empty
        ) {
            if (it) {
                Score(
                    state = state,
                    modifier = Modifier
                        .padding(bottom = Padding.extra.small)
                        .padding(bottom = Padding.small)
                )
            }
        }

        test.forEachIndexed { index, question ->
            Question(
                index = index,
                question = question,
                tag = state.tag,
                focusRequester = focusRequesters[index],
                onKeyboardAction = onKeyboardAction,
                modifier = fillMaxWidth
            )
        }

        val isInProgress = state.tag.isInProgress

        val interactionSource = remember {
            MutableInteractionSource()
        }

        val isFocused by interactionSource.collectIsFocusedAsState()
        val containerColor by animateColorAsState(
            targetValue = when {
                isInProgress.not() -> colorScheme.surface
                else -> when {
                    isFocused -> colorScheme.primary
                    else -> colorScheme.surface
                }
            },
            label = String.empty
        )

        val contentColor by animateColorAsState(
            targetValue = when {
                isInProgress.not() -> colorScheme.onSurface
                else -> when {
                    isFocused -> colorScheme.onPrimary
                    else -> colorScheme.onSurface
                }
            },
            label = String.empty
        )

        val visible = when (state) {
            is TestState.Preparing -> false
            else -> state.isAllAnswered
        }

        AnimatedVisibility(visible = visible) {
            ElevatedCard(
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
                    .padding(vertical = Padding.small)
                    .focusRequester(focusRequesters.last())
                    .focusable(interactionSource = interactionSource),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = containerColor,
                    contentColor = contentColor
                )
            ) {
                Box(
                    modifier = fillMaxWidth.padding(
                        horizontal = Padding.medium,
                        vertical = with(Padding) {
                            extra.small.plus(small)
                        }
                    )
                ) {
                    Crossfade(
                        targetState = isInProgress.not(),
                        modifier = Modifier.align(Alignment.CenterStart),
                        label = String.empty
                    ) { targetState ->
                        Icon(
                            id = when {
                                targetState -> R.drawable.round_restart_alt_24
                                else -> R.drawable.round_check_24
                            }
                        )
                    }

                    Text(
                        text = when {
                            isInProgress -> stringResource(id = R.string.check)
                            else -> stringResource(id = R.string.solve_again)
                        } ,
                        modifier = Modifier
                            .animateContentSize()
                            .align(Alignment.Center),
                        fontWeight = FontWeight.Bold,
                        style = typography.bodyMedium
                    )
                }
            }
        }
    }
}
