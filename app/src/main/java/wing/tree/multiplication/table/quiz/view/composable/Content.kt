package wing.tree.multiplication.table.quiz.view.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import kotlinx.collections.immutable.persistentListOf
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.constant.INVALID_INDEX
import wing.tree.multiplication.table.constant.NUMBER_OF_QUESTIONS
import wing.tree.multiplication.table.extension.empty
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.full
import wing.tree.multiplication.table.extension.fullyTransparent
import wing.tree.multiplication.table.extension.`is`
import wing.tree.multiplication.table.extension.medium
import wing.tree.multiplication.table.extension.not
import wing.tree.multiplication.table.extension.two
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.quiz.model.Question
import wing.tree.multiplication.table.quiz.state.QuizState

@Composable
internal fun Content(
    state: QuizState,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val quiz = state.quiz
        val focusManager = LocalFocusManager.current
        val focusRequesters = persistentListOf(
            *Array(quiz.size) {
                FocusRequester()
            }
        )

        val onKeyboardAction: (Action.Keyboard) -> Unit = remember(state) {
            { keyboardAction ->
                when (keyboardAction) {
                    Action.Keyboard.Done -> {
                        focusManager.clearFocus()
                        onAction(Action.Check)
                    }

                    is Action.Keyboard.Next -> {
                        var index = quiz.withIndex().indexOfFirst { indexedValue ->
                            when {
                                indexedValue.index <= keyboardAction.index -> false
                                else -> indexedValue.value.unanswered
                            }
                        }

                        if (index `is` INVALID_INDEX) {
                            index = quiz.indexOfFirst(Question::unanswered)
                        }

                        if (index not INVALID_INDEX) {
                            focusRequesters[index].requestFocus()
                        }
                    }
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(Int.two),
            modifier = Modifier.weight(Float.full),
            contentPadding = PaddingValues(vertical = Dp.extraSmall),
            verticalArrangement = Arrangement.spacedBy(Dp.extraSmall),
            horizontalArrangement = Arrangement.spacedBy(Dp.extraSmall)
        ) {
            val imeAction = quiz.count {
                it.answered
            }
                .let {
                    if (it < NUMBER_OF_QUESTIONS) {
                        ImeAction.Next
                    } else {
                        ImeAction.Done
                    }
                }

            item(
                span = {
                    GridItemSpan(Int.two)
                }
            ) {
                AnimatedContent(
                    targetState = state is QuizState.Checked,
                    transitionSpec = {
                        val enter = expandHorizontally().plus(fadeIn())
                        val exit = fadeOut().plus(shrinkHorizontally())

                        enter togetherWith exit
                    },
                    label = String.empty
                ) {
                    if (it) {
                        Score(state = state)
                    }
                }
            }

            itemsIndexed(quiz) { index, question ->
                Question(
                    index = index,
                    question = question,
                    tag = state.tag,
                    focusRequester = focusRequesters[index],
                    imeAction = imeAction,
                    onKeyboardAction = onKeyboardAction
                )
            }

            item {
                FloatingActionButton(
                    onClick = {
                        noOperations
                    },
                    modifier = Modifier
                        .alpha(Float.fullyTransparent)
                        .padding(top = Dp.medium),
                    content = noOperations
                )
            }
        }
    }
}
