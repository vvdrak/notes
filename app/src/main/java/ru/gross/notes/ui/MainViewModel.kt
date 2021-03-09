package ru.gross.notes.ui

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gross.notes.R
import ru.gross.notes.common.Event
import ru.gross.notes.common.asEvent
import ru.gross.notes.ui.list.NoteView
import ru.gross.notes.utils.stringResource
import javax.inject.Inject

/**
 * Описывает главную [ViewModel] приложения.
 * @author gross_va
 */
class MainViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {
    /**
     * Заголовок приложения.
     */
    val title: LiveData<String> = MutableLiveData(stringResource(R.string.app_name))

    /**
     * Текущая заметка, выбранная пользователем.
     */
    val current: LiveData<Event<Screen>> = MutableLiveData(Screen.LIST.asEvent())

    /**
     * Действие пользователя.
     */
    val userAction: LiveData<Event<Action>> = MutableLiveData()

    /**
     * Устанавливает переданный в аргументах [note] как основной.
     * В случае, если [note] == `null`, основным станет новый экземпляр [NoteView]
     */
    fun setAsCurrent(view: View, note: NoteView?) {
//        val content = NavMove(note ?: NoteView(), view)
//        (current as MutableLiveData).value = content.asEvent()
//        val titleRes = if (note == null) R.string.new_note_text else R.string.app_name
//        (title as MutableLiveData).value = stringResource(titleRes)
    }
}