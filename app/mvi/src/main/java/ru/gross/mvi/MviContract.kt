package ru.gross.mvi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ViewState
interface ViewEvent
interface ViewEffect

interface MviContract<TState : ViewState, TEffect : ViewEffect> {
    val viewModel: MviViewModel<TState, *, TEffect>

    fun handleViewEffect(effect: TEffect)
    fun renderViewState(state: TState)
}

suspend fun <TState : ViewState> MviContract<TState, *>.collectState() {
    viewModel.viewState.collect {
        withContext(Dispatchers.Main) {
            renderViewState(it)
        }
    }
}

suspend fun <TEffect : ViewEffect> MviContract<*, TEffect>.collectEffect() {
    viewModel.viewEffect.collect {
        withContext(Dispatchers.Main) {
            handleViewEffect(it)
        }
    }
}