package ru.gross.notes.mapper

import ru.gross.notes.db.entity.NoteEntity
import ru.gross.notes.domain.Note
import javax.inject.Inject

class NoteEntityMapper @Inject constructor() : Mapper<NoteEntity?, Note?> {
    override fun apply(input: NoteEntity?): Note? = input?.let {
        Note(
            id = it.id,
            creationDate = it.creationDate,
            title = it.title,
            content = it.content
        )
    }
}