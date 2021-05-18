package ru.gross.notes.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ru.gross.notes.db.entity.NoteEntity
import java.util.*

/**
 * Описывает DAO для [заметки][NoteEntity]
 *
 * @author gross_va
 */
@Dao
abstract class NotesDao {

    /**
     * Возвращает список заметок.
     */
    @Query("SELECT * FROM note_table")
    abstract suspend fun getNotesSuspend(): List<NoteEntity>

    /**
     * Возвращает [заметку][NoteEntity] по ее идентификатору.
     * @param id Идентификтаор заметки.
     */
    @Query("SELECT * FROM note_table WHERE note_id = :id")
    abstract suspend fun getByIdSuspend(id: String): NoteEntity?

    @Update(entity = NoteEntity::class)
    abstract suspend fun updateSuspend(entity: NoteEntity)

    /**
     * Обновляет информацию в заметке.
     */
    @Transaction
    open suspend fun updateWithTransaction(
        id: String?,
        title: String?,
        content: String?,
    ) {
        val note = id?.let { getByIdSuspend(it) } ?: NoteEntity(id = newId(), creationDate = Date())
        note.content = content
        note.title = title
        updateSuspend(note)
    }

    private companion object {
        @JvmStatic
        fun newId(): String = UUID.randomUUID().toString()
    }
}
