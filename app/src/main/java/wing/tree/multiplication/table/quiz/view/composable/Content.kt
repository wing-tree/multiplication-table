package wing.tree.multiplication.table.quiz.view.composable

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import kotlinx.collections.immutable.persistentListOf
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.Icon
import wing.tree.multiplication.table.constant.INVALID_INDEX
import wing.tree.multiplication.table.constant.NUMBER_OF_QUESTIONS
import wing.tree.multiplication.table.extension.empty
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.isLessThan
import wing.tree.multiplication.table.extension.function.isLessThanOrEqualTo
import wing.tree.multiplication.table.extension.function.not
import wing.tree.multiplication.table.extension.function.verticalFadingEdge
import wing.tree.multiplication.table.extension.medium
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.marginValues
import wing.tree.multiplication.table.extension.property.pair
import wing.tree.multiplication.table.extension.small
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.quiz.state.QuizState

@Composable
internal fun Content(
    state: QuizState,
    widthSizeClass: WindowWidthSizeClass,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    val quiz = state.quiz
    val focusManager = LocalFocusManager.current
    val focusRequesters = remember(state) {
        persistentListOf(
            *Array(quiz.size.inc()) {
                FocusRequester()
            }
        )
    }

    val onKeyboardAction: (Action.Keyboard) -> Unit = remember(state) {
        { keyboardAction ->
            when (keyboardAction) {
                is Action.Keyboard.Next -> {
                    val count = quiz.count {
                        it.answered
                    }

                    if (count isLessThan NUMBER_OF_QUESTIONS) {
                        var index = quiz.withIndex().indexOfFirst { indexedValue ->
                            when {
                                indexedValue.index isLessThanOrEqualTo keyboardAction.index -> false
                                else -> indexedValue.value.unanswered
                            }
                        }

                        if (index `is` INVALID_INDEX) {
                            index = quiz.indexOfFirst(Question::unanswered)
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
        modifier = modifier
            .verticalScroll(scrollState)
            .verticalFadingEdge(scrollState)
            .padding(vertical = Dp.extraSmall)
            .padding(paddingValues = widthSizeClass.marginValues),
        verticalArrangement = Arrangement.spacedBy(Dp.extraSmall)
    ) {
        AnimatedContent(
            targetState = state is QuizState.Checked,
            modifier = Modifier.fillMaxWidth(),
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
                    modifier = Modifier.padding(bottom = Dp.small)
                )
            }
        }

        quiz.withIndex().chunked(Int.pair).forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dp.extraSmall)
            ) {
                it.forEach { (index, question) ->
                    Question(
                        index = index,
                        question = question,
                        tag = state.tag,
                        focusRequester = focusRequesters[index],
                        onKeyboardAction = onKeyboardAction,
                        modifier = Modifier.weight(Float.`1`)
                    )
                }
            }
        }

        val checked = when (state.tag) {
            QuizState.Tag.CHECKED -> true
            QuizState.Tag.CLEARING -> true
            else -> false
        }

        val interactionSource = remember {
            MutableInteractionSource()
        }

        val isFocused by interactionSource.collectIsFocusedAsState()
        val containerColor by animateColorAsState(
            targetValue = if (checked) {
                colorScheme.surface
            } else {
                if (isFocused) {
                    colorScheme.primary
                } else {
                    colorScheme.surface
                }
            },
            label = String.empty
        )

        val contentColor by animateColorAsState(
            targetValue = if (checked) {
                colorScheme.onSurface
            } else {
                if (isFocused) {
                    colorScheme.onPrimary
                } else {
                    colorScheme.onSurface
                }
            },
            label = String.empty
        )

        val visible = when (state) {
            is QuizState.Clearing -> false
            else -> state.allAnswered
        }

        AnimatedVisibility(visible = visible) {
            ElevatedCard(
                onClick = {
                    val action = when (state) {
                        is QuizState.Checked -> Action.SolveAgain
                        else -> {
                            focusManager.clearFocus()

                            Action.Check
                        }
                    }

                    onAction(action)
                },
                modifier = Modifier
                    .padding(vertical = Dp.extraSmall)
                    .focusRequester(focusRequesters.last())
                    .focusable(interactionSource = interactionSource),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = containerColor,
                    contentColor = contentColor
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = Dp.medium,
                            vertical = Dp.small
                        )
                ) {
                    Crossfade(
                        targetState = checked,
                        modifier = Modifier.align(Alignment.CenterStart),
                        label = String.empty
                    ) { targetState ->
                        Icon(
                            id = if (targetState) {
                                R.drawable.round_restart_alt_24
                            } else {
                                R.drawable.round_check_24
                            }
                        )
                    }

                    Text(
                        text = if (checked) {
                            stringResource(id = R.string.solve_again)
                        } else {
                            stringResource(id = R.string.check)
                        },
                        modifier = Modifier
                            .animateContentSize()
                            .align(Alignment.Center),
                        fontWeight = FontWeight.Bold,
                        style = typography.labelLarge
                    )
                }
            }
        }
    }
}
