package ru.gross.notes.repository

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.State
import ru.gross.notes.common.asState
import ru.gross.notes.db.NotesDao
import ru.gross.notes.domain.Note
import ru.gross.notes.mapper.NoteEntityMapper
import javax.inject.Inject

/**
 * Реализация репозитория заметок.
 * Данная реализация имеет зависимости конкретного фреймворка, поэтому вынесена в самый верхний уровень.
 * @author gross_va
 */
class NotesRepositoryImpl @Inject constructor(
    private val dao: NotesDao,
    private val entityMapper: NoteEntityMapper
) : NotesRepository {
    override fun getById(id: String): Flow<State<Note?>> = stateFlow {
        val source = dao.getById(id)
            .run(entityMapper::apply)
            .asState()
        emit(source)
    }

    override fun getAll(): Flow<State<List<Note>?>> = stateFlow {
        val source = dao.getAll()
            .mapNotNull(entityMapper::apply)
            .asState()
        emit(source)
    }
}