package ru.gross.notes.ui.detail

import androidx.lifecycle.*
import ru.gross.notes.common.Resource
import ru.gross.notes.common.mapResourceFlow
import ru.gross.notes.common.value
import ru.gross.notes.interactors.DisplayNoteDetail
import ru.gross.notes.interactors.UpdateNote
import ru.gross.notes.mapper.NoteDetailViewMapper
import javax.inject.Inject

class NoteDetailsViewModel(
    noteId: String?,
    noteDetailMapper: NoteDetailViewMapper,
    displayNoteDetail: DisplayNoteDetail,
    private val updateNote: UpdateNote
) : ViewModel() {

    /**
     * Детальная информация заметки.
     */
    val details: LiveData<Resource<NoteDetailView?>> =
        displayNoteDetail(noteId)
            .mapResourceFlow(noteDetailMapper::apply)
            .asLiveData(viewModelScope.coroutineContext)

    /**
     * Сохраняет изменения в заметке.
     */
    fun saveChanges() {
        details.value?.value?.let {
            val args = UpdateNote.Args(it.id, it.title, it.content)
            updateNote(args)
        }
    }

    class Factory @Inject constructor(
        private val displayNoteDetail: DisplayNoteDetail,
        private val noteDetailMapper: NoteDetailViewMapper,
        private val updateNote: UpdateNote
    ) : ViewModelProvider.Factory {
        private var noteId: String? = null
        fun setNoteId(noteId: String?) = apply { this.noteId = noteId }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            NoteDetailsViewModel(
                noteId,
                noteDetailMapper,
                displayNoteDetail,
                updateNote
            ) as T
    }
}