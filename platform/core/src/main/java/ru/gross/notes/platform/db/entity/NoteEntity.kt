package ru.gross.notes.platform.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note_table")
internal data class NoteEntity(
    /**
     * Идентификатор заметки.
     */
    @PrimaryKey
    @ColumnInfo(name = "note_id")
    val id: String,

    /**
     * Дата создания
     */
    @ColumnInfo(name = "note_creation_date")
    val creationDate: Date? = null,

    /**
     * Заголовок заметки.
     */
    @ColumnInfo(name = "note_title")
    var title: String? = null,

    /**
     * Текст заметки.
     */
    @ColumnInfo(name = "note_content")
    var content: String? = null
)
