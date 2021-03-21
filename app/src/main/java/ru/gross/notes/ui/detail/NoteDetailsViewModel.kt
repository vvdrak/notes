package ru.gross.notes.ui.detail

import androidx.lifecycle.*
import ru.gross.notes.interactors.DisplayNoteDetail
import ru.gross.notes.common.State
import ru.gross.notes.common.mapState
import ru.gross.notes.mapper.NoteDetailMapper
import javax.inject.Inject

class NoteDetailsViewModel(
    noteId: String,
    displayNoteDetail: DisplayNoteDetail,
    noteDetailMapper: NoteDetailMapper
) : ViewModel() {

    /**
     * Детальная информация заметки.
     */
    val details: LiveData<State<NoteDetailView?>> =
        displayNoteDetail(noteId)
            .mapState(noteDetailMapper::apply)
            .asLiveData(viewModelScope.coroutineContext)

    class Factory @Inject constructor(
        private val displayNoteDetail: DisplayNoteDetail,
        private val noteDetailMapper: NoteDetailMapper
    ) : ViewModelProvider.Factory {
        private var noteId: String? = null
        fun setNoteId(noteId: String?) = apply { this.noteId = noteId }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            requireNotNull(noteId) { "Note id not set" }.let {
                NoteDetailsViewModel(
                    it,
                    displayNoteDetail,
                    noteDetailMapper
                ) as T
            }
    }
}