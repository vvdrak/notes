package ru.gross.notes.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.gross.notes.ui.Screen
import ru.gross.notes.ui.detail.NoteDetailScreen
import ru.gross.notes.ui.list.DisplayNotesScreen
import ru.gross.notes.ui.list.NoteView
import javax.inject.Inject


private const val START_DESTINATION_ROUTE = "navigation_display_notes"

private const val DETAIL_NAV_ARGUMENT_NAME = "noteId"
private const val DETAIL_NAV_ROUTE_ROOT = "navigation_detail_note"
private const val DETAIL_DESTINATION_ROUTE = "$DETAIL_NAV_ROUTE_ROOT/{$DETAIL_NAV_ARGUMENT_NAME}"

private const val ADD_NOTE_ROUTE = "navigation_add_note"

internal class ComposeNavigator @Inject constructor() : Navigator {
    var latestAction by mutableStateOf<NavAction?>(null)
        private set

    override fun showNoteDetail(viewState: NoteView) {
        latestAction = NavAction.Navigate("$DETAIL_NAV_ROUTE_ROOT/${viewState.id}")
    }

    override fun showAddNote() {
        latestAction = NavAction.Navigate(ADD_NOTE_ROUTE)
    }

    override fun navigateUp(): Boolean {
        latestAction = NavAction.NavigateUp
        return true
    }
}

internal sealed class NavAction {
    data class Navigate(val route: String) : NavAction()
    object NavigateUp : NavAction()
}

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    navController: NavHostController,
) {
    HandleNavAction(navigator as ComposeNavigator, navController)
    val startDestination = START_DESTINATION_ROUTE
    val graph = remember(navController, startDestination) {
        NavGraphBuilder(
            navController.navigatorProvider,
            startDestination,
            null
        ).apply {
            composable(route = startDestination) {
                DisplayNotesScreen(onNavigateNoteDetail = navigator::showNoteDetail)
            }

            composable(
                route = DETAIL_DESTINATION_ROUTE,
                arguments = listOf(
                    navArgument(DETAIL_NAV_ARGUMENT_NAME) { type = NavType.StringType }
                )
            ) {
                NoteDetailScreen(onNavigateUp = navigator::navigateUp)
            }

            composable(route = ADD_NOTE_ROUTE) {
                NoteDetailScreen(onNavigateUp = navigator::navigateUp)
            }
        }.build()
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        graph = graph
    )
}

@Composable
internal fun HandleRouteChange(navController: NavController, onChange: (Screen) -> Unit) {
    val currentOnChange by rememberUpdatedState(onChange)
    val backCallback = remember {
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.route == START_DESTINATION_ROUTE -> {
                    currentOnChange(Screen.List)
                }
                destination.route?.startsWith(DETAIL_NAV_ROUTE_ROOT) == true -> {
                    currentOnChange(Screen.Detail)
                }
                destination.route == ADD_NOTE_ROUTE -> {
                    currentOnChange(Screen.Add)
                }
            }
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, navController) {
        navController.addOnDestinationChangedListener(backCallback)
        onDispose {
            navController.removeOnDestinationChangedListener(backCallback)
        }
    }
}

@Composable
private fun HandleNavAction(
    navigator: ComposeNavigator,
    navController: NavHostController,
) {
    val latestAction = navigator.latestAction
    LaunchedEffect(latestAction) {
        when (latestAction) {
            null -> Unit
            is NavAction.Navigate -> {
                navController.navigate(latestAction.route)
            }
            NavAction.NavigateUp -> {
                navController.navigateUp()
            }
        }
    }
}