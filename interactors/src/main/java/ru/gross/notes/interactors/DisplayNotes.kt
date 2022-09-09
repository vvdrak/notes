package ru.gross.notes.interactors

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.Resource
import ru.gross.notes.domain.Note
import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Описывает реализацию варианта использования *Отображение заметок*
 *
 * @author gross_va
 */
class DisplayNotes @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Note>>> = repository.getAll()
}