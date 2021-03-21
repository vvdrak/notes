package ru.gross.notes.repository

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.State
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
    fun getById(id: String): Flow<State<Note?>>

    /**
     * Возвращает список заметок.
     */
    fun getAll(): Flow<State<List<Note>?>>
}