package ru.gross.notes.ui

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import ru.gross.notes.R
import ru.gross.notes.common.Event
import ru.gross.notes.common.NavMove
import ru.gross.notes.common.State
import ru.gross.notes.common.asEvent
import ru.gross.notes.interactors.DisplayNotes
import ru.gross.notes.model.Note
import ru.gross.notes.utils.stringResource
import javax.inject.Inject

/**
 * Описывает главную [ViewModel] приложения.
 * @author gross_va
 */
class MainViewModel @Inject constructor(
    application: Application,
    displayNotes: DisplayNotes
) : AndroidViewModel(application) {
    /**
     * Заголовок приложения.
     */
    val title: LiveData<String> = MutableLiveData(stringResource(R.string.app_name))

    /**
     * Список заметок.
     */
    val notes: LiveData<State<List<Note>>> = displayNotes(null)
        .asLiveData(viewModelScope.coroutineContext)

    /**
     * Текущая заметка, выбранная пользователем.
     */
    val current: LiveData<Event<NavMove<Note>>> = MutableLiveData()

    /**
     * Устанавливает переданный в аргументах [note] как основной.
     * В случае, если [note] == `null`, основным станет новый экземпляр [Note]
     */
    fun setAsCurrent(view: View, note: Note?) {
        val content = NavMove(note ?: Note(), view)
        (current as MutableLiveData).value = content.asEvent()
        val titleRes = if (note == null) R.string.new_note_text else R.string.app_name
        (title as MutableLiveData).value = stringResource(titleRes)
    }
}