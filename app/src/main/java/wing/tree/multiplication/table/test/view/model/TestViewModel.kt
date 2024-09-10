package wing.tree.multiplication.table.test.view.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import wing.tree.multiplication.table.ad.InterstitialAdLoader
import wing.tree.multiplication.table.base.view.model.BaseViewModel
import wing.tree.multiplication.table.extension.property.`3`
import wing.tree.multiplication.table.extension.property.`7`
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.model.Key
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.test.intent.TestEvent
import wing.tree.multiplication.table.test.intent.TestSideEffect
import wing.tree.multiplication.table.test.intent.TestState
import wing.tree.multiplication.table.test.model.Test
import kotlin.time.Duration.Companion.milliseconds

class TestViewModel(savedStateHandle: SavedStateHandle) : BaseViewModel<TestState, TestSideEffect>() {
    private val endInclusive = checkNotNull(savedStateHandle.get<Int>(Key.END_INCLUSIVE()))
    private val start = checkNotNull(savedStateHandle.get<Int>(Key.START()))

    override val initialState: TestState = TestState.InProgress(Test(start = start, endInclusive = endInclusive))

    fun onEvent(event: TestEvent) {
        when (event) {
            is TestEvent.Click -> onClick(event)
        }
    }

    private fun check() {
        reduce {
            when (it) {
                is TestState.InProgress -> when {
                    it.allAnswered -> {
                        viewModelScope.launch {
                            delay(timeMillis = Long.`7`.hundreds)

                            if (InterstitialAdLoader.adLoaded) {
                                postSideEffect(TestSideEffect.Show.InterstitialAd)
                            }
                        }

                        TestState.Completed(it.test)
                    }
                    else -> it
                }

                else -> it
            }
        }
    }

    private fun onClick(event: TestEvent.Click) {
        when (event) {
            TestEvent.Click.Check -> check()
            TestEvent.Click.Home -> viewModelScope.launch {
                postSideEffect(TestSideEffect.Navigate.ToHome)
            }

            TestEvent.Click.SolveAgain -> solveAgain()
        }
    }

    private fun solveAgain() {
        viewModelScope.launch {
            reduce {
                when (it) {
                    is TestState.Completed -> it.copy(tag = TestState.Tag.CLEARING)
                    else -> it
                }
            }

            delay(Long.`3`.hundreds.milliseconds)

            reduce {
                TestState.InProgress(it.test.onEach(Question::clear))
            }
        }
    }
}
