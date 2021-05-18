package ru.gross.notes.repository

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.Resource
import ru.gross.notes.domain.Note

/**
 * Описывает репоизторий заметок.
 *
 * @author gross_va
 */
interface NotesRepository : Repository {

    /**
     * Возвращает [заметку][Note] по ее идентификатору.
     * @param id Идентификтаор заметки.
     */
    fun getById(id: String): Flow<Resource<Note?>>

    /**
     * Возвращает список заметок.
     */
    fun getAll(): Flow<Resource<List<Note>?>>

    /**
     * Обновляет информацию в заметке.
     */
    fun update(id: String?, title: String?, content: String?)
}