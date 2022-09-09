package ru.gross.notes.platform.mapper

import ru.gross.notes.domain.Note
import ru.gross.notes.mapper.Mapper
import ru.gross.notes.platform.db.entity.NoteEntity
import javax.inject.Inject

internal class NoteEntityMapper @Inject constructor() : Mapper<NoteEntity, Note> {
    override fun invoke(input: NoteEntity): Note = input.let {
        Note(
            id = it.id,
            creationDate = it.creationDate,
            title = it.title,
            content = it.content
        )
    }
}