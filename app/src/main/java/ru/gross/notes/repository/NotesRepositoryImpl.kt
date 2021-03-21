package ru.gross.notes.repository

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.State
import ru.gross.notes.domain.Note

/**
 * Реализация репозитория заметок.
 * Данная реализация имеет зависимости конкретного фреймворка, поэтому вынесена в самый верхний уровень.
 * @author gross_va
 */
class NotesRepositoryImpl : NotesRepository {
    override fun getById(id: String): Flow<State<Note?>> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<State<List<Note>?>> {
        TODO("Not yet implemented")
    }
}