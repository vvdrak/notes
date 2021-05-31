package ru.gross.notes.ui.detail

import androidx.lifecycle.*
import ru.gross.notes.common.Resource
import ru.gross.notes.common.mapResourceFlow
import ru.gross.notes.common.value
import ru.gross.notes.interactors.DeleteNote
import ru.gross.notes.interactors.DisplayNoteDetail
import ru.gross.notes.interactors.ShareNote
import ru.gross.notes.interactors.UpdateNote
import ru.gross.notes.mapper.NoteDetailViewMapper
import javax.inject.Inject

class NoteDetailsViewModel(
    noteId: String?,
    noteDetailMapper: NoteDetailViewMapper,
    displayNoteDetail: DisplayNoteDetail,
    private val updateNote: UpdateNote,
    private val shareNote: ShareNote,
    private val deleteNote: DeleteNote
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

    /**
     * Отправляет заметку
     */
    fun shareIt() {
        details.value?.value?.id
            ?.let { shareNote(ShareNote.Args(viewModelScope, it)) }
    }

    /**
     * Удаляет заметку
     */
    fun deleteIt() {
        details.value?.value?.id
            ?.let { deleteNote(it) }
    }

    class Factory @Inject constructor(
        private val displayNoteDetail: DisplayNoteDetail,
        private val noteDetailMapper: NoteDetailViewMapper,
        private val updateNote: UpdateNote,
        private val shareNote: ShareNote,
        private val deleteNote: DeleteNote
    ) : ViewModelProvider.Factory {
        private var noteId: String? = null
        fun setNoteId(noteId: String?) = apply { this.noteId = noteId }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            NoteDetailsViewModel(
                noteId,
                noteDetailMapper,
                displayNoteDetail,
                updateNote,
                shareNote,
                deleteNote
            ) as T
    }
}