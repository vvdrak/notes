package ru.gross.notes.mapper

import ru.gross.notes.domain.Note
import ru.gross.notes.ui.detail.NoteDetailView
import javax.inject.Inject

internal class NoteDetailViewMapper @Inject constructor() :
    Mapper<Note, NoteDetailView> {
    override fun invoke(input: Note): NoteDetailView =
        input.let {
            NoteDetailView(
                id = it.id,
                creationDate = it.creationDate,
                title = it.title.orEmpty(),
                content = it.content.orEmpty()
            )
        }
}