package ru.gross.notes.ui

import ru.gross.mvi.ViewEffect
import ru.gross.mvi.ViewEvent
import ru.gross.mvi.ViewState

internal data class State(
    val screen: Screen = Screen.List
) : ViewState

internal sealed class Effect : ViewEffect {
    object DisplayAddNote : Effect()
    object NavigateBack : Effect()
    object DisplayStub : Effect()
}

internal sealed class Event : ViewEvent {
    data class SetCurrentScreen(val screen: Screen) : Event()
    sealed class ClickEvent : Event() {
        object AddNote : ClickEvent()
        object GoBack : ClickEvent()
        object ShareNote : ClickEvent()
        object DeleteNote : ClickEvent()
        object Search : ClickEvent()
        object Menu : ClickEvent()
    }
}

/**
 * Описывает доступные экраны приложения.
 * @author gross_va
 */
internal sealed class Screen {
    object List : Screen()
    object Detail : Screen()
    object Add : Screen()
}