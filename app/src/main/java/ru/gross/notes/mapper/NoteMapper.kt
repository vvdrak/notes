package ru.gross.notes.mapper

import ru.gross.notes.model.Note
import ru.gross.notes.ui.list.NoteView
import javax.inject.Inject

class NoteMapper @Inject constructor(): Mapper<Note?, NoteView?> {
    override fun apply(input: Note?): NoteView? =
        input?.let { NoteView(it.id, it.title, it.content) }
}