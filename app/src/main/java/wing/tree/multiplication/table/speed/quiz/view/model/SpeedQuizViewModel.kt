package wing.tree.multiplication.table.speed.quiz.view.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import wing.tree.multiplication.table.ad.InterstitialAdLoader
import wing.tree.multiplication.table.base.view.model.BaseViewModel
import wing.tree.multiplication.table.countdown.timer.CountdownTimer
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`7`
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.extension.property.thirtySecondsInMilliseconds
import wing.tree.multiplication.table.model.Key
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.model.SpeedQuiz
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizSideEffect
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizState

class SpeedQuizViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SpeedQuizState, SpeedQuizSideEffect>() {
    override val initialState: SpeedQuizState = SpeedQuizState.Preparing

    private val countdownTimer = object : CountdownTimer(
        millisecondsInFuture = millisecondsInFuture,
        countDownInterval = Long.`1`
    ) {
        override fun onFinish() {
            finish()
        }

        override fun onTick(millisecondsUntilFinished: Long) {
            with(state.value) {
                if (this is SpeedQuizState.Play.Playing) {
                    elapsedTime = millisecondsInFuture.minus(millisecondsUntilFinished)
                }
            }
        }
    }

    private val endInclusive = checkNotNull(savedStateHandle.get<Int>(Key.END_INCLUSIVE()))
    private val start = checkNotNull(savedStateHandle.get<Int>(Key.START()))

    override fun onCleared() {
        countdownTimer.stop()
        countdownTimer.cancel()

        super.onCleared()
    }

    fun onAction(action: SpeedQuizAction) {
        when (action) {
            SpeedQuizAction.Home -> viewModelScope.launch {
                postSideEffect(SpeedQuizSideEffect.Home)
            }

            SpeedQuizAction.OnReady -> play()
            SpeedQuizAction.SolveNew -> reduce {
                SpeedQuizState.Preparing
            }

            is SpeedQuizAction.Next -> with(state.value) {
                if (this is SpeedQuizState.Play.Playing) {
                    speedQuiz.submit(action.question)
                }
            }
        }
    }

    private fun finish() {
        if (InterstitialAdLoader.adLoaded) {
            viewModelScope.launch {
                delay(timeMillis = Long.`7`.hundreds)

                postSideEffect(SpeedQuizSideEffect.Show.InterstitialAd)
            }
        }

        reduce {
            when (it) {
                is SpeedQuizState.Play.Playing -> it.finish()
                else -> it
            }
        }

        countdownTimer.stop()
    }

    private fun play() {
        reduce {
            SpeedQuizState.Play.Playing(
                speedQuiz = SpeedQuiz(start = start, endInclusive = endInclusive)
            )
        }

        countdownTimer.start()
    }

    companion object {
        val millisecondsInFuture = Long.thirtySecondsInMilliseconds
    }
}
