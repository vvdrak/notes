package ru.gross.notes.ui

import androidx.lifecycle.*
import ru.gross.notes.common.Event
import ru.gross.notes.common.State
import ru.gross.notes.interactors.DisplayNotes
import ru.gross.notes.model.Note
import javax.inject.Inject

/**
 * Описывает главную [ViewModel] приложения.
 * @author gross_va
 */
class MainViewModel @Inject constructor(
    displayNotes: DisplayNotes
) : ViewModel() {

    /**
     * Список заметок.
     */
    val notes: LiveData<State<List<Note>>> = displayNotes(null)
        .asLiveData(viewModelScope.coroutineContext)

    /**
     * Текущая заметка, выбранная пользователем.
     */
    val current: LiveData<Event<Note>> = MutableLiveData()
}