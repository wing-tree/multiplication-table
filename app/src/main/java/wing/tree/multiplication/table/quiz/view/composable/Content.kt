package wing.tree.multiplication.table.quiz.view.composable

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
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
import wing.tree.multiplication.table.extension.fifth
import wing.tree.multiplication.table.extension.full
import wing.tree.multiplication.table.extension.`is`
import wing.tree.multiplication.table.extension.isLessThan
import wing.tree.multiplication.table.extension.isLessThanOrEqualTo
import wing.tree.multiplication.table.extension.medium
import wing.tree.multiplication.table.extension.not
import wing.tree.multiplication.table.extension.pair
import wing.tree.multiplication.table.extension.seventh
import wing.tree.multiplication.table.extension.sixth
import wing.tree.multiplication.table.extension.small
import wing.tree.multiplication.table.extension.verticalFadingEdge
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.quiz.model.Question
import wing.tree.multiplication.table.quiz.state.QuizState
import wing.tree.multiplication.table.theme.palette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Content(
    state: QuizState,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .verticalFadingEdge(scrollState)
                .padding(vertical = Dp.extraSmall)
            ,
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
                            modifier = Modifier.weight(Float.full)
                        )
                    }
                }
            }

            val checked = state.tag `is` QuizState.Tag.CHECKED
            val interactionSource = remember {
                MutableInteractionSource()
            }

            val isFocused by interactionSource.collectIsFocusedAsState()
            val containerColor by animateColorAsState(
                targetValue = if (checked) {
                    palette.seventh()
                } else {
                    if (isFocused) {
                        palette.sixth()
                    } else {
                        palette.fifth()
                    }
                },
                label = String.empty
            )

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
                    .focusable(
                        enabled = state.allAnswered,
                        interactionSource = interactionSource
                    ),
                colors = CardDefaults.elevatedCardColors(containerColor = containerColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = Dp.medium, vertical = Dp.small)
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
