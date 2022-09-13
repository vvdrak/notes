package ru.gross.notes.ui.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.gross.mvi.ViewEffect
import ru.gross.mvi.ViewEvent
import ru.gross.mvi.ViewState

internal sealed class State : ViewState {
    object LoadingList : State()
    data class DisplayList(
        val list: List<NoteView>
    ) : State()
}

internal sealed class Event : ViewEvent {
    data class ClickNote(val note: NoteView) : Event()
}

internal sealed class Effect : ViewEffect {
    data class DisplayNote(val note: NoteView) : Effect()
}

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
    val id: String,

    /**
     * Заголовок заметки.
     */
    @JvmField
    val title: String,

    /**
     * Текст заметки.
     */
    @JvmField
    val content: String
) : Parcelable
