package ru.gross.notes.ui

import android.content.res.Configuration
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import ru.gross.notes.R
import ru.gross.notes.navigation.HandleRouteChange
import ru.gross.notes.navigation.MainNavHost
import ru.gross.notes.navigation.Navigator
import ru.gross.notes.ui.theme.ApplicationTheme
import ru.gross.notes.utils.collectState
import ru.gross.notes.utils.onEffect

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    navigator: Navigator,
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val onBackPressedDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher

    val navController = rememberNavController()
    HandleRouteChange(navController) {
        viewModel.submitEvent(Event.SetCurrentScreen(it))
    }

    viewModel.onEffect {
        when (it) {
            Effect.DisplayAddNote -> {
                navigator.showAddNote()
            }
            Effect.DisplayStub -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = context.getString(R.string.feature_not_available_text)
                )
            }
            Effect.NavigateBack -> {
                if (onBackPressedDispatcher.hasEnabledCallbacks()) {
                    onBackPressedDispatcher.onBackPressed()
                } else {
                    navigator.navigateUp()
                }
            }
        }
    }

    val state by viewModel.collectState()
    MainScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        screen = state.screen,
        navigationIconClick = { viewModel.submitEvent(Event.ClickEvent.GoBack) },
        fabClick = { viewModel.submitEvent(Event.ClickEvent.AddNote) },
        onDeleteClick = { viewModel.submitEvent(Event.ClickEvent.DeleteNote) },
        onShareClick = { viewModel.submitEvent(Event.ClickEvent.ShareNote) },
        onMenuClick = { viewModel.submitEvent(Event.ClickEvent.Menu) },
        onSearchClick = { viewModel.submitEvent(Event.ClickEvent.Search) }
    ) {
        MainNavHost(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            navigator = navigator,
            navController = navController
        )
    }
}

@Composable
private fun MainScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    screen: Screen = Screen.List,
    navigationIconClick: () -> Unit = {},
    fabClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
        topBar = {
            CentredTitleTopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = stringResource(id = R.string.app_name)
                    )
                },
                navigationIcon = when (screen) {
                    Screen.List -> null
                    else -> @Composable {
                        { AppIconButton(Icons.Filled.ArrowBack, onClick = navigationIconClick) }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                contentColor = MaterialTheme.colors.onPrimary
            ) {
                when (screen) {
                    Screen.Detail -> {
                        Spacer(Modifier.weight(1f, true))
                        AppIconButton(Icons.Outlined.Delete, onDeleteClick)
                        AppIconButton(Icons.Filled.Share, onShareClick)
                    }
                    Screen.List -> {
                        AppIconButton(Icons.Filled.Menu, onMenuClick)
                        Spacer(Modifier.weight(1f, true))
                        AppIconButton(Icons.Filled.Search, onSearchClick)
                    }
                    else -> Unit
                }
            }
        },
        floatingActionButton = {
            when (screen) {
                Screen.List -> {
                    ExtendedFloatingActionButton(
                        text = { Text(text = stringResource(id = R.string.add_note_text).uppercase()) },
                        onClick = fabClick
                    )
                }
                else -> Unit
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = content
    )
}

@Composable
private fun PreviewMainScaffold() = ApplicationTheme() {
    MainScaffold(screen = Screen.Detail)
}

@Preview(
    device = Devices.PIXEL_4,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewMainScreenNight() = PreviewMainScaffold()

@Preview(
    device = Devices.PIXEL_4,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewMainScreenDay() = PreviewMainScaffold()