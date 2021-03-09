package ru.gross.notes.data.model

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
    val id: String? = null,

    /**
     * Дата создания
     */
    @JvmField
    val creationDate: Date? = null,

    /**
     * Заголовок заметки.
     */
    @JvmField
    var title: CharSequence? = null,

    /**
     * Текст заметки.
     */
    @JvmField
    var content: CharSequence? = null
)