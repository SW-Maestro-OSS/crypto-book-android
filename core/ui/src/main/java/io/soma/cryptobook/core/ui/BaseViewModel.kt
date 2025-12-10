package io.soma.cryptobook.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update


interface UiState
interface Event
interface SideEffect

abstract class BaseViewModel<EVENT: Event, UI_STATE: UiState, SIDE_EFFECT: SideEffect>(
    initialState: UI_STATE
) : ViewModel() {
    private val _state: MutableStateFlow<UI_STATE> = MutableStateFlow(initialState)
    val state: StateFlow<UI_STATE> = _state.asStateFlow()

    private val _sideEffect: Channel<SIDE_EFFECT> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    protected fun setUiState(reduce: UI_STATE.() -> UI_STATE) {
        val newState = _state.value.reduce()
        _state.update { newState }
    }

    protected fun sendSideEffect(builder: () -> SIDE_EFFECT) {
        viewModelScope.launch {
            _sideEffect.send(builder())
        }
    }

    abstract fun handleEvent(event: EVENT)
}