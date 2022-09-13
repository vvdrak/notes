package ru.gross.notes.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import ru.gross.notes.R
import ru.gross.notes.ui.list.DisplayNotesFragmentDirections
import ru.gross.notes.ui.list.NoteView
import ru.gross.notes.utils.navigate
import javax.inject.Inject

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

class NavigatorImpl @Inject constructor(
    private val activity: FragmentActivity
) : Navigator {

    override fun showNoteDetail(viewState: NoteView) {
        val id = requireNotNull(viewState.id) { "Note id not set" }
        navigate(
            navController = activity.findNavController(R.id.nav_host_fragment),
            direction = DisplayNotesFragmentDirections.toNoteCard(id),
        )
    }

    override fun showAddNote() {
        navigate(
            navController = activity.findNavController(R.id.nav_host_fragment),
            direction = DisplayNotesFragmentDirections.toNoteCard(null),
        )
    }

    override fun navigateUp(): Boolean =
        activity.findNavController(R.id.nav_host_fragment).navigateUp()
}