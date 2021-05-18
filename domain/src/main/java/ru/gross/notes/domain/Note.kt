package ru.gross.notes.domain

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
    var title: String? = null,

    /**
     * Текст заметки.
     */
    @JvmField
    var content: String? = null
)