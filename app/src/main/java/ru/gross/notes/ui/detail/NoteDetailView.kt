package ru.gross.notes.ui.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class NoteDetailView(
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
) : Parcelable {

    /**
     * Возвращает флаг заполненности заметки.
     */
    val isFilled: Boolean
        get() = id != null || creationDate != null || title != null || content != null
}
