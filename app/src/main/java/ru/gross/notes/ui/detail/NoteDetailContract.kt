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
    data class SaveChanges(val confirmed: Boolean) : Event()
}

internal sealed class Effect : ViewEffect {
    object DisplayDeleteDialog : Effect()
    object GoBack : Effect()
    object DisplaySaveDialog : Effect()
}

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