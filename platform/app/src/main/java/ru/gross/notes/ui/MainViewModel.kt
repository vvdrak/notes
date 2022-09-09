package ru.gross.notes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.gross.mvi.MviViewModel
import ru.gross.notes.interactors.NotifyDeleteNote
import ru.gross.notes.interactors.NotifyShareNote
import javax.inject.Inject

/**
 * Описывает главную [ViewModel] приложения.
 * @author gross_va
 */
@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val notifyDeleteNote: NotifyDeleteNote,
    private val notifyShareNote: NotifyShareNote
) : MviViewModel<State, Event, Effect>(State()) {

    override fun submitEvent(event: Event) {
        when (event) {
            Event.ClickEvent.AddNote -> postEffect(Effect.DisplayAddNote)
            Event.ClickEvent.GoBack -> postEffect(Effect.NavigateBack)
            Event.ClickEvent.DeleteNote -> {
                viewModelScope.launch { notifyDeleteNote() }
            }
            Event.ClickEvent.ShareNote -> {
                viewModelScope.launch { notifyShareNote() }
            }
            Event.ClickEvent.Menu -> postEffect(Effect.DisplayStub)
            Event.ClickEvent.Search -> postEffect(Effect.DisplayStub)
            is Event.SetCurrentScreen -> {
                state = state.copy(
                    screen = event.screen
                )
            }
        }
    }
}