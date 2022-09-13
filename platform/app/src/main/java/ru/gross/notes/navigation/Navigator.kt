package ru.gross.notes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.gross.notes.ui.detail.NoteDetailScreen
import ru.gross.notes.ui.list.DisplayNotesScreen
import ru.gross.notes.ui.list.NoteView
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

internal class NavigatorImpl @Inject constructor() : Navigator {

    lateinit var navHostController: NavHostController
        private set

    val startDestination: String
        get() = START_DESTINATION_ROUTE

    override fun showNoteDetail(viewState: NoteView) {
        navHostController.navigate("$DETAIL_NAV_ROUTE_ROOT/${viewState.id}")
    }

    override fun showAddNote() {
        navHostController.navigate(ADD_NOTE_ROUTE)
    }

    override fun navigateUp(): Boolean =
        navHostController.navigateUp()


    internal fun setNavHostController(navHostController: NavHostController) {
        this.navHostController = navHostController
    }

    internal fun createGraph(builder: NavGraphBuilder) {
        builder.composable(route = START_DESTINATION_ROUTE) {
            DisplayNotesScreen(navigator = this)
        }

        builder.composable(
            route = DETAIL_DESTINATION_ROUTE,
            arguments = listOf(
                navArgument(DETAIL_NAV_ARGUMENT_NAME) { type = NavType.StringType }
            )
        ) {
            NoteDetailScreen(navigator = this)
        }

        builder.composable(route = ADD_NOTE_ROUTE) {
            NoteDetailScreen(navigator = this)
        }
    }

    internal companion object {
        const val START_DESTINATION_ROUTE = "navigation_display_notes"

        const val DETAIL_NAV_ARGUMENT_NAME = "noteId"
        const val DETAIL_NAV_ROUTE_ROOT = "navigation_detail_note"
        const val DETAIL_DESTINATION_ROUTE = "$DETAIL_NAV_ROUTE_ROOT/{$DETAIL_NAV_ARGUMENT_NAME}"

        const val ADD_NOTE_ROUTE = "navigation_add_note"
    }
}