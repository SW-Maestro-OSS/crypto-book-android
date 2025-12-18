package io.soma.cryptobook.settings.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<STATE : Any, SIDE_EFFECT : Any>(
    val initialState: STATE,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow: StateFlow<STATE> = _stateFlow.asStateFlow()

    val state: STATE get() = _stateFlow.value

    private val _sideEffectChannel = Channel<SIDE_EFFECT>(Channel.BUFFERED)
    val sideEffectFlow: Flow<SIDE_EFFECT> = _sideEffectChannel.receiveAsFlow()

    protected fun intent(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }


    fun reduce(redcuer: STATE.() -> STATE) {
        _stateFlow.value = _stateFlow.value.redcuer()
    }

    suspend fun postSideEffect(sideEffect: SIDE_EFFECT) {
        _sideEffectChannel.send(sideEffect)
    }
}