package ru.gross.notes.mapper

import ru.gross.notes.domain.Note
import ru.gross.notes.ui.list.NoteView
import javax.inject.Inject

internal class NoteViewListMapper @Inject constructor() : Mapper<List<Note>, List<NoteView>> {
    override fun invoke(input: List<Note>): List<NoteView> =
        input.map { NoteView(it.id, it.title, it.content) }
}