package wing.tree.multiplication.table.test.view.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import wing.tree.multiplication.table.extension.property.`3`
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.model.Key
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.test.intent.TestSideEffect
import wing.tree.multiplication.table.test.model.Test
import wing.tree.multiplication.table.test.intent.TestState
import kotlin.time.Duration.Companion.milliseconds

class TestViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val endInclusive = checkNotNull(savedStateHandle.get<Int>(Key.END_INCLUSIVE()))
    private val start = checkNotNull(savedStateHandle.get<Int>(Key.START()))
    private val _sideEffect = Channel<TestSideEffect>()
    private val _state = MutableStateFlow<TestState>(
        TestState.InProgress(Test(start = start, endInclusive = endInclusive))
    )

    val sideEffect = _sideEffect.receiveAsFlow()
    val state = _state.asStateFlow()

    fun check() {
        _state.update {
            when (it) {
                is TestState.InProgress -> when {
                    it.allAnswered -> TestState.Completed(it.test)
                    else -> it
                }

                else -> it
            }
        }
    }

    fun solveAgain() {
        viewModelScope.launch {
            _state.update {
                when (it) {
                    is TestState.Completed -> it.copy(tag = TestState.Tag.CLEARING)
                    else -> it
                }
            }

            delay(Long.`3`.hundreds.milliseconds)

            _state.update {
                TestState.InProgress(it.test.onEach(Question::clear))
            }
        }
    }
}
