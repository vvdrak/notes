package ru.gross.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<TState : ViewState, TEvent : ViewEvent, TEffect : ViewEffect>(
    initial: TState
) : ViewModel() {
    private val mViewState = MutableSharedFlow<TState>(1, 0, BufferOverflow.DROP_OLDEST)
    private val mViewEffect = MutableSharedFlow<TEffect>(0, 1, BufferOverflow.DROP_OLDEST)

    val viewEffect: Flow<TEffect>
        get() = mViewEffect

    val viewState: Flow<TState>
        get() = mViewState

    init {
        viewModelScope.launch {
            mViewState.emit(initial)
        }
    }

    var state: TState = initial
        @Synchronized
        get
        @Synchronized
        protected set(value) {
            field = value
            viewModelScope.launch {
                mViewState.emit(value)
            }
        }

    abstract fun submitEvent(event: TEvent)

    protected fun postEffect(effect: TEffect) {
        viewModelScope.launch {
            mViewEffect.emit(effect)
        }
    }
}