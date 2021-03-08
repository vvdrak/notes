package ru.gross.notes.mapper

import ru.gross.notes.model.Note
import ru.gross.notes.ui.detail.NoteDetailView
import javax.inject.Inject

class NoteDetailMapper @Inject constructor() : Mapper<Note?, NoteDetailView?> {
    override fun apply(input: Note?): NoteDetailView? =
        input?.let { NoteDetailView(it.id, it.creationDate, it.title, it.content) }
}