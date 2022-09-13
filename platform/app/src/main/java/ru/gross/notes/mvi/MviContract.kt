package ru.gross.notes.mvi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gross.mvi.MviViewModel
import ru.gross.mvi.ViewEffect
import ru.gross.mvi.ViewState

internal interface MviContract<TState : ViewState, TEffect : ViewEffect> {
    val viewModel: MviViewModel<TState, *, TEffect>

    fun handleViewEffect(effect: TEffect)
    fun renderViewState(state: TState)
}

internal suspend fun <TState : ViewState> MviContract<TState, *>.collectState() {
    viewModel.viewState.collect {
        withContext(Dispatchers.Main) {
            renderViewState(it)
        }
    }
}

internal suspend fun <TEffect : ViewEffect> MviContract<*, TEffect>.collectEffect() {
    viewModel.viewEffect.collect {
        withContext(Dispatchers.Main) {
            handleViewEffect(it)
        }
    }
}