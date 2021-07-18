package ru.gross.notes.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
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
class NotesRepositoryImpl @Inject constructor(
    private val dao: NotesDao,
    private val entityMapper: NoteEntityMapper,
) : NotesRepository {
    override fun getById(id: String?): Flow<Resource<Note>> = resourceFlow {
        val source = id?.let {
            dao.getByIdSuspend(it)
                .run(entityMapper::apply)
        } ?: Note()
        emit(source.asResource())
    }

    override fun getAll(): Flow<Resource<List<Note>>> = resourceFlow {
        val source = dao.getNotes()
            .map { it.mapNotNull(entityMapper::apply).asResource() }
        emitAll(source)
    }

    override fun update(id: String?, title: String?, content: String?) = execute {
        dao.updateWithTransaction(id, title, content)
    }

    override fun remove(id: String) = execute {
        dao.remove(id)
    }
}