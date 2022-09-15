package ru.gross.notes.navigation

import ru.gross.notes.ui.list.NoteView

/**
 * Описывает навигатор приложения.
 * @author gross_va
 */
interface Navigator {

    /**
     * Отображает детальную информацию о заметке.
     * @param viewState Объект модели представления, связанный с переходом на детальную информацию о заметке.
     */
    fun showNoteDetail(viewState: NoteView)

    /**
     * Отображает экран добавления заметки.
     */
    fun showAddNote()

    /**
     * Отображает предыдущий экран или выходит из приложения.
     */
    fun navigateUp(): Boolean
}