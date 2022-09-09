package ru.gross.notes.ui.list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.gross.mvi.MviViewModel
import ru.gross.notes.common.handle
import ru.gross.notes.common.mapResourceFlow
import ru.gross.notes.interactors.DisplayNotes
import ru.gross.notes.mapper.NoteViewListMapper
import javax.inject.Inject

@HiltViewModel
internal class NotesViewModel @Inject constructor(
    displayNotes: DisplayNotes,
    mapNote: NoteViewListMapper
) : MviViewModel<State, Event, Effect>(State.LoadingList) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            displayNotes(null)
                .mapResourceFlow(mapNote::invoke)
                .collect { resource ->
                    resource.handle(
                        loadingHandler = {
                            state = State.LoadingList
                        },
                        successHandler = {
                            state = State.DisplayList(it)
                        }
                    )
                }
        }
    }

    override fun submitEvent(event: Event) {
        when (event) {
            is Event.ClickNote -> postEffect(Effect.DisplayNote(event.note))
        }
    }
}