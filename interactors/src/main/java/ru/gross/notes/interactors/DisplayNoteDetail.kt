package ru.gross.notes.interactors

import ru.gross.notes.common.Resource
import ru.gross.notes.domain.Note
import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Описывает реализацию варианта использования *Отображение подробной информации о заметке*.
 *
 * @author gross_va
 */
class DisplayNoteDetail @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(id: String?): Resource<Note> = repository.getById(id)
}