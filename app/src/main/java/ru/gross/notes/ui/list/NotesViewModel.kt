package ru.gross.notes.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.gross.notes.common.Resource
import ru.gross.notes.common.mapResourceFlow
import ru.gross.notes.interactors.DisplayNotes
import ru.gross.notes.mapper.NoteViewMapper
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    displayNotes: DisplayNotes,
    mapNote: NoteViewMapper
) : ViewModel() {

    /**
     * Список заметок.
     */
    val notes: LiveData<Resource<List<NoteView?>>> = displayNotes(null)
        .mapResourceFlow { it.map(mapNote::apply) }
        .asLiveData(viewModelScope.coroutineContext)
}