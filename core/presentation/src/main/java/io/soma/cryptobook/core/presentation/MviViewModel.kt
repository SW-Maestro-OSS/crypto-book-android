package io.soma.cryptobook.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiState
interface Event
interface SideEffect

abstract class MviViewModel<EVENT : Event, STATE : UiState, SIDE_EFFECT : SideEffect>(
    initialState: STATE,
) : ViewModel() {
    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    val currentState: STATE get() = _state.value

    private val _sideEffect: Channel<SIDE_EFFECT> = Channel(Channel.BUFFERED)
    val sideEffect: Flow<SIDE_EFFECT> = _sideEffect.receiveAsFlow()

    protected fun reduce(reducer: STATE.() -> STATE) {
        _state.update { it.reducer() }
    }

    protected fun sendSideEffect(effect: SIDE_EFFECT) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }

    abstract fun handleEvent(event: EVENT)

    protected fun intent(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}
