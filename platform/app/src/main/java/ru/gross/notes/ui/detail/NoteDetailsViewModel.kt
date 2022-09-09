package ru.gross.notes.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.gross.mvi.MviViewModel
import ru.gross.notes.common.handle
import ru.gross.notes.interactors.*
import ru.gross.notes.mapper.NoteDetailViewMapper
import ru.gross.notes.platform.interactors.ShareNote

internal class NoteDetailsViewModel constructor(
    noteId: String?,
    noteDetailMapper: NoteDetailViewMapper,
    displayNoteDetail: DisplayNoteDetail,
    private val updateNote: UpdateNote,
    private val shareNote: ShareNote,
    private val deleteNote: DeleteNote,
    private val notifyDeleteNote: NotifyDeleteNote,
    private val notifyShareNote: NotifyShareNote
) : MviViewModel<State, Event, Effect>(State.LoadDetail) {

    private val note: NoteDetailView?
        get() = (state as? State.DisplayDetail)?.detail

    init {
        viewModelScope.launch(Dispatchers.IO) {
            displayNoteDetail(noteId).handle(
                loadingHandler = {
                    state = State.LoadDetail
                },
                successHandler = {
                    state = State.DisplayDetail(noteDetailMapper(it))
                })
        }

        viewModelScope.launch(Dispatchers.IO) {
            notifyShareNote.onShareEvent.collect {
                this@NoteDetailsViewModel.note?.id
                    ?.let { shareNote(it) }
            }
        }

        viewModelScope.launch {
            notifyDeleteNote.onDeleteEvent.collect {
                postEffect(Effect.DisplayDeleteDialog)
            }
        }
    }

    override fun submitEvent(event: Event) {
        when (event) {
            Event.DeleteNote -> note?.id?.let {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteNote(it)
                }
            }
            is Event.SaveChanges -> note?.let {
                when {
                    !it.isFilled -> {
                        postEffect(Effect.GoBack)
                    }
                    it.id == null && it.isFilled && !event.confirmed -> {
                        postEffect(Effect.DisplaySaveDialog)
                    }
                    else -> {
                        viewModelScope.launch(Dispatchers.IO) {
                            val args = UpdateNote.Args(it.id, it.title, it.content)
                            updateNote(args)
                            postEffect(Effect.GoBack)
                        }
                    }
                }
            }
        }
    }

    class Factory @AssistedInject constructor(
        @Assisted("noteId") private var noteId: String?,
        private val displayNoteDetail: DisplayNoteDetail,
        private val noteDetailMapper: NoteDetailViewMapper,
        private val updateNote: UpdateNote,
        private val shareNote: ShareNote,
        private val deleteNote: DeleteNote,
        private val notifyDeleteNote: NotifyDeleteNote,
        private val notifyShareNote: NotifyShareNote
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NoteDetailsViewModel(
                noteId,
                noteDetailMapper,
                displayNoteDetail,
                updateNote,
                shareNote,
                deleteNote,
                notifyDeleteNote,
                notifyShareNote
            ) as T
        }

        @dagger.assisted.AssistedFactory
        interface AssistedFactory {
            fun create(@Assisted("noteId") noteId: String?): Factory
        }
    }
}