package ru.gross.notes.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import ru.gross.mvi.MviViewModel
import ru.gross.mvi.ViewEffect
import ru.gross.mvi.ViewState

@Composable
fun <TViewState : ViewState> MviViewModel<TViewState, *, *>.collectState() =
    viewState.collectAsState(initial = state, Dispatchers.Main)

@SuppressLint("ComposableNaming")
@Composable
fun <TViewEffect : ViewEffect> MviViewModel<*, *, TViewEffect>.onEffect(
    handler: suspend (effect: TViewEffect) -> Unit
) = LaunchedEffect(key1 = this) {
    viewEffect.collectLatest { handler(it) }
}