package ru.gross.notes.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.gross.notes.db.entity.NoteEntity
import java.util.*

/**
 * Описывает DAO для [заметки][NoteEntity]
 *
 * @author gross_va
 */
@Dao
internal abstract class NotesDao {

    /**
     * Возвращает список заметок в формате Hot Flow.
     */
    @Query("SELECT * FROM note_table ORDER BY note_creation_date")
    abstract fun getNotes(): Flow<List<NoteEntity>>

    /**
     * Возвращает [заметку][NoteEntity] по ее идентификатору.
     * @param id Идентификтаор заметки.
     */
    @Query("SELECT * FROM note_table WHERE note_id = :id")
    abstract suspend fun getByIdSuspend(id: String): NoteEntity?

    /**
     * Обновляет информацию в заметке [entity].
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateSuspend(entity: NoteEntity)

    /**
     * Обновляет [title] и [content] в заметке с идентификатором [id].
     * В случае отсутствия заметки с идентификаторм [id] будет добавлена новая заметка.
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

    /**
     * Удаляет заметку с идентификатором [id]
     */
    @Query("DELETE FROM note_table WHERE note_id = :id")
    abstract suspend fun remove(id: String)

    private companion object {
        @JvmStatic
        fun newId(): String = UUID.randomUUID().toString()
    }
}
