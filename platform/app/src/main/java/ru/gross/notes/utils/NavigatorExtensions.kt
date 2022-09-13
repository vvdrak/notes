package ru.gross.notes.utils

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ru.gross.notes.navigation.Navigator
import ru.gross.notes.navigation.NavigatorImpl
import ru.gross.notes.ui.Screen

internal val Navigator.navController: NavHostController
    get() = (this as NavigatorImpl).navHostController

internal val Navigator.startDestination: String
    get() = (this as NavigatorImpl).startDestination

internal fun Navigator.createGraph(builder: NavGraphBuilder) =
    (this as NavigatorImpl).createGraph(builder)

internal fun Navigator.attachToNavController(
    navController: NavHostController
) = (this as NavigatorImpl).run {
    setNavHostController(navController)
}

@Composable
internal fun HandleRouteChange(navigator: Navigator, onChange: (Screen) -> Unit) {
    val currentOnChange by rememberUpdatedState(onChange)
    val backCallback = remember {
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.route == NavigatorImpl.START_DESTINATION_ROUTE -> {
                    currentOnChange(Screen.List)
                }
                destination.route?.startsWith(NavigatorImpl.DETAIL_NAV_ROUTE_ROOT) == true -> {
                    currentOnChange(Screen.Detail)
                }
                destination.route == NavigatorImpl.ADD_NOTE_ROUTE -> {
                    currentOnChange(Screen.Add)
                }
            }
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, navigator.navController) {
        navigator.navController.addOnDestinationChangedListener(backCallback)
        onDispose {
            navigator.navController.removeOnDestinationChangedListener(backCallback)
        }
    }
}
