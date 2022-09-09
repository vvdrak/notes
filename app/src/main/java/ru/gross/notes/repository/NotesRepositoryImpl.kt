package ru.gross.notes.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gross.notes.common.Resource
import ru.gross.notes.common.asResource
import ru.gross.notes.db.NotesDao
import ru.gross.notes.domain.Note
import ru.gross.notes.mapper.NoteEntityMapper
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
    override suspend fun getById(id: String?): Resource<Note> {
        val source = id?.let {
            dao.getByIdSuspend(it)?.run(entityMapper::invoke)
        } ?: Note()
        return source.asResource()
    }

    override suspend fun getAll(): Flow<Resource<List<Note>>> =
        dao.getNotes().map { it.map(entityMapper::invoke).asResource() }

    override suspend fun update(id: String?, title: String?, content: String?) =
        dao.updateWithTransaction(id, title, content)

    override suspend fun remove(id: String) = dao.remove(id)
}