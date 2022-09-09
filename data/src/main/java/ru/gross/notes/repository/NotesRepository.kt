package ru.gross.notes.repository

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.Resource
import ru.gross.notes.domain.Note

/**
 * Описывает репоизторий заметок.
 *
 * @author gross_va
 */
interface NotesRepository {

    /**
     * Возвращает [заметку][Note] по ее идентификатору.
     * В случае передачи *null* вернет пустой объект для добавления.
     * @param id Идентификтаор заметки.
     */
    suspend fun getById(id: String?): Resource<Note>

    /**
     * Возвращает список заметок.
     */
    suspend fun getAll(): Flow<Resource<List<Note>>>

    /**
     * Обновляет информацию в заметке.
     */
    suspend fun update(id: String?, title: String?, content: String?)

    /**
     * Удаляет [заметку][Note] с идентификатором [id]
     */
    suspend fun remove(id: String)
}