package ru.gross.notes.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ru.gross.notes.common.State
import ru.gross.notes.common.mapState
import ru.gross.notes.interactors.DisplayNotes
import ru.gross.notes.mapper.NoteViewMapper
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    displayNotes: DisplayNotes,
    mapNote: NoteViewMapper
) : ViewModel() {

    /**
     * Список заметок.
     */
    val notes: LiveData<State<List<NoteView?>>> = displayNotes(null)
        .mapState { it?.map(mapNote::apply) ?: emptyList() }
        .asLiveData(viewModelScope.coroutineContext)
}