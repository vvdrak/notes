package ru.gross.notes.ui.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * ViewState для отображения заметки.
 * @author gross_va
 */
@Parcelize
data class NoteView(
    /**
     * Идентификатор заметки.
     */
    @JvmField
    val id: String? = null,

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
