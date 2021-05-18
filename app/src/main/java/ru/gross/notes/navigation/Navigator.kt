package ru.gross.notes.navigation

import android.content.Context
import android.content.Intent
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
     * Оттправляет текстовое содержимое в другие приложения.
     * @param context Контекст приложения.
     * @param text Текст, отправляемый в другие приложения.
     */
    fun shareText(context: Context, text: String)

    /**
     * Отображает детальную информацию о заметке.
     * @param activity Активити приложения.
     * @param view Представление, инициировавшее переход. Зачастую необходимо для шаред анимации.
     * @param viewState Объект модели представления, связанный с переходом на детальную информацию о заметке.
     */
    fun showNoteDetail(activity: FragmentActivity, view: View, viewState: NoteView)
}

class NavigatorImpl @Inject constructor() : Navigator {

    override fun shareText(context: Context, text: String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(sendIntent, null))
    }

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
}