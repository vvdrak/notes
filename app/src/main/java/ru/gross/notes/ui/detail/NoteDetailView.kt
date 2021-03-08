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
    var title: CharSequence? = null,

    /**
     * Текст заметки.
     */
    @JvmField
    var content: CharSequence? = null
) : Parcelable
