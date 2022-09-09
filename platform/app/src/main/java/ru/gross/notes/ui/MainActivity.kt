package ru.gross.notes.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.dataBindings
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.gross.mvi.MviActivity
import ru.gross.notes.R
import ru.gross.notes.databinding.ActivityMainBinding
import ru.gross.notes.navigation.Navigator
import ru.gross.notes.ui.detail.DetailNoteFragmentArgs
import ru.gross.notes.utils.navigateUp
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : MviActivity<State, Effect>() {
    @Inject
    lateinit var navigator: Navigator

    override val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by dataBindings(R.layout.activity_main)
    private val navController by lazy(mode = LazyThreadSafetyMode.NONE) {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        //WindowCompat.setDecorFitsSystemWindows(window, false)

        with(binding) {
            fab.setOnClickListener {
                viewModel.submitEvent(Event.ClickEvent.AddNote)
            }
            toolbar.setNavigationOnClickListener {
                viewModel.submitEvent(Event.ClickEvent.GoBack)
            }
            with(bottomBar) {
                setNavigationOnClickListener {
                    viewModel.submitEvent(Event.ClickEvent.Menu)
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_share -> {
                            viewModel.submitEvent(Event.ClickEvent.ShareNote)
                            true
                        }
                        R.id.action_delete -> {
                            viewModel.submitEvent(Event.ClickEvent.DeleteNote)
                            true
                        }
                        R.id.navigation_search -> {
                            viewModel.submitEvent(Event.ClickEvent.Search)
                            true
                        }
                        else -> false
                    }
                }
            }
            navController.addOnDestinationChangedListener { _, dest, args ->
                viewModel.submitEvent(
                    Event.SetCurrentScreen(
                        when (dest.id) {
                            R.id.navigation_detail_note -> {
                                val bundle = requireNotNull(args)
                                val fragmentArgs = DetailNoteFragmentArgs.fromBundle(bundle)
                                if (fragmentArgs.noteId == null) Screen.Add else Screen.Detail
                            }
                            else -> Screen.List
                        }
                    )
                )
            }
        }
    }

    override fun handleViewEffect(effect: Effect) {
        when (effect) {
            Effect.DisplayAddNote -> navigator.showAddNote()
            Effect.NavigateBack -> {
                if (onBackPressedDispatcher.hasEnabledCallbacks()) {
                    onBackPressedDispatcher.onBackPressed()
                } else {
                    navigateUp(navController)
                }
            }
            Effect.DisplayStub -> displayStubMessage()
        }
    }

    override fun renderViewState(state: State) {
        with(binding) {
            title = getString(R.string.app_name)
            when (val screen = state.screen) {
                Screen.Detail, Screen.Add -> {
                    fab.hide()
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24px)
                    with(bottomBar) {
                        navigationIcon = null
                        if (screen is Screen.Add) {
                            menu.clear()
                        } else if (screen is Screen.Detail) {
                            replaceMenu(R.menu.detail_note_menu)
                        }
                    }
                }
                else -> {
                    fab.show()
                    toolbar.navigationIcon = null
                    with(bottomBar) {
                        setNavigationIcon(R.drawable.ic_menu_24px)
                        replaceMenu(R.menu.bottom_appbar_menu)
                    }
                }
            }
        }
    }
}