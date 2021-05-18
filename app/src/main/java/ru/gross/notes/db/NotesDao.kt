package ru.gross.notes.db

import androidx.room.Dao
import androidx.room.Query
import ru.gross.notes.db.entity.NoteEntity

/**
 * Описывает DAO для [заметки][NoteEntity]
 *
 * @author gross_va
 */
@Dao
interface NotesDao {

    /**
     * Возвращает список заметок.
     */
    @Query("SELECT * FROM note_table")
    suspend fun getAll(): List<NoteEntity>

    /**
     * Возвращает [заметку][NoteEntity] по ее идентификатору.
     * @param id Идентификтаор заметки.
     */
    @Query("SELECT * FROM note_table WHERE note_id = :id")
    suspend fun getById(id: String): NoteEntity?
}
