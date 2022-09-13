package ru.gross.notes.ui.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.gross.mvi.ViewEffect
import ru.gross.mvi.ViewEvent
import ru.gross.mvi.ViewState
import java.util.*

internal sealed class State : ViewState {
    object LoadDetail : State()
    data class DisplayDetail(
        val detail: NoteDetailView
    ) : State()
}

internal sealed class Event : ViewEvent {
    object DeleteNote : Event()
    object GoBack : Event()
    data class SaveChanges(val confirmed: Boolean) : Event()
    sealed class NewText : Event() {
        abstract val value: String

        data class Title(override val value: String) : NewText()
        data class Content(override val value: String) : NewText()
    }
}

internal sealed class Effect : ViewEffect {
    object DisplayDeleteDialog : Effect()
    object GoBack : Effect()
    object DisplaySaveDialog : Effect()
}

@Parcelize
data class NoteDetailView(
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
    val title: String? = null,

    /**
     * Текст заметки.
     */
    @JvmField
    val content: String? = null
) : Parcelable {

    /**
     * Возвращает флаг заполненности заметки.
     */
    val isFilled: Boolean
        get() = id != null || creationDate != null || title != null || content != null
}