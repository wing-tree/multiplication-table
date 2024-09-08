package wing.tree.multiplication.table.speed.quiz.view.model

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
import wing.tree.multiplication.table.ad.InterstitialAdLoader
import wing.tree.multiplication.table.countdown.timer.CountdownTimer
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`7`
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.extension.property.thirtySecondsInMilliseconds
import wing.tree.multiplication.table.model.Key
import wing.tree.multiplication.table.speed.quiz.action.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.model.SpeedQuiz
import wing.tree.multiplication.table.speed.quiz.side.effect.SpeedQuizSideEffect
import wing.tree.multiplication.table.speed.quiz.state.SpeedQuizState

class SpeedQuizViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _sideEffect = Channel<SpeedQuizSideEffect>()
    private val _state = MutableStateFlow<SpeedQuizState>(SpeedQuizState.Ready)

    val sideEffect = _sideEffect.receiveAsFlow()
    val state = _state.asStateFlow()

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
                _sideEffect.send(SpeedQuizSideEffect.Home)
            }

            SpeedQuizAction.OnReady -> play()
            SpeedQuizAction.Replay -> prepare()
            is SpeedQuizAction.Next -> with(state.value) {
                if (this is SpeedQuizState.Play.Playing) {
                    speedQuiz.submit(action.question)
                }
            }
        }
    }

    private fun finish() {
        if (InterstitialAdLoader.isAdLoaded) {
            viewModelScope.launch {
                delay(timeMillis = Long.`7`.hundreds)

                _sideEffect.send(SpeedQuizSideEffect.Show.InterstitialAd)
            }
        }

        _state.update {
            when (it) {
                is SpeedQuizState.Play.Playing -> it.finish()
                else -> it
            }
        }

        countdownTimer.stop()
    }

    private fun play() {
        _state.update {
            SpeedQuizState.Play.Playing(
                speedQuiz = SpeedQuiz(start = start, endInclusive = endInclusive)
            )
        }

        countdownTimer.start()
    }

    private fun prepare() {
        _state.update {
            SpeedQuizState.Ready
        }
    }

    companion object {
        val millisecondsInFuture = Long.thirtySecondsInMilliseconds
    }
}
