package wing.tree.multiplication.table.base.view.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<STATE, SIDE_EFFECT> : ViewModel() {
    abstract val initialState: STATE

    private val _state by lazy {
        MutableStateFlow(initialState)
    }

    val state by lazy {
        _state.asStateFlow()
    }

    private val `this`: BaseViewModel<STATE, SIDE_EFFECT> get() = this
    private val sideEffect = Channel<SIDE_EFFECT>()

    @Composable
    fun collectSideEffect(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        sideEffect: (suspend (sideEffect: SIDE_EFFECT) -> Unit)
    ) {
        val collector by rememberUpdatedState(sideEffect)
        val flow = remember {
            `this`.sideEffect.receiveAsFlow()
        }

        val lifecycleOwner = LocalLifecycleOwner.current

        LaunchedEffect(lifecycleOwner) {
            lifecycleOwner.lifecycle.repeatOnLifecycle(state) {
                flow.collect(collector)
            }
        }
    }

    protected fun reduce(reducer: (STATE) -> STATE) {
        _state.update {
            reducer(it)
        }
    }

    protected suspend fun postSideEffect(sideEffect: SIDE_EFFECT) {
        `this`.sideEffect.send(sideEffect)
    }

}
