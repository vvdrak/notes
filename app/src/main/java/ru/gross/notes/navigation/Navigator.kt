package ru.gross.notes.navigation

import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
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
     * @param activity Активити приложения.
     * @param view Представление, инициировавшее переход. Зачастую необходимо для шаред анимации.
     * @param viewState Объект модели представления, связанный с переходом на детальную информацию о заметке.
     */
    fun showNoteDetail(activity: FragmentActivity, view: View, viewState: NoteView)

    /**
     * Отображает экран добавления заметки.
     * @param activity Активити приложения.
     */
    fun showAddNote(activity: FragmentActivity)
}

class NavigatorImpl @Inject constructor() : Navigator {

    override fun showNoteDetail(activity: FragmentActivity, view: View, viewState: NoteView) {
        val id = requireNotNull(viewState.id) { "Note id not set" }
        val transitionName = ViewCompat.getTransitionName(view)
        val extras = if (transitionName != null) FragmentNavigatorExtras(view to transitionName) else null
        navigate(
            activity.findNavController(R.id.nav_host_fragment),
            DisplayNotesFragmentDirections.toNoteCard(id),
            navExtras = extras
        )
    }

    override fun showAddNote(activity: FragmentActivity) {
        navigate(
            activity.findNavController(R.id.nav_host_fragment),
            DisplayNotesFragmentDirections.toNoteCard(null),
        )
    }
}