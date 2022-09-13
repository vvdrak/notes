package ru.gross.notes.platform.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gross.notes.common.Resource
import ru.gross.notes.common.asResource
import ru.gross.notes.domain.Note
import ru.gross.notes.platform.db.NotesDao
import ru.gross.notes.platform.mapper.NoteEntityMapper
import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Реализация репозитория заметок.
 * Данная реализация имеет зависимости конкретного фреймворка, поэтому вынесена в самый верхний уровень.
 * @author gross_va
 */
internal class NotesRepositoryImpl @Inject constructor(
    private val dao: NotesDao,
    private val entityMapper: NoteEntityMapper,
) : NotesRepository {
    override suspend fun getById(id: String): Resource<Note> {
        return dao.getByIdSuspend(id)
            ?.run(entityMapper::invoke)
            ?.asResource() ?: Resource.error("$NOTHING_ERROR_TEXT $id")
    }

    override suspend fun getAll(): Flow<Resource<List<Note>>> =
        dao.getNotes().map { it.map(entityMapper::invoke).asResource() }

    override suspend fun update(id: String?, title: String?, content: String?) =
        dao.updateWithTransaction(id, title, content)

    override suspend fun remove(id: String) = dao.remove(id)

    private companion object {
        private const val NOTHING_ERROR_TEXT = "No Note with id"
    }
}