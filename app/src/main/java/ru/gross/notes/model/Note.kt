package ru.gross.notes.model

import java.time.Instant
import java.util.*

/**
 * Описывает заметку.
 * @author gross_va
 */
data class Note(
    /**
     * Идентификатор заметки.
     */
    @JvmField
    val id: String,

    /**
     * Дата создания
     */
    @JvmField
    val creationDate: Date,

    /**
     * Заголовок заметки.
     */
    @JvmField
    var title: CharSequence,

    /**
     * Текст заметки.
     */
    @JvmField
    var content: CharSequence
)