package ru.gross.notes.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.dataBindings
import androidx.navigation.findNavController
import ru.gross.notes.R
import ru.gross.notes.databinding.ActivityMainBinding
import ru.gross.notes.di.InjectableViewModelFactory
import ru.gross.notes.navigation.Navigator
import ru.gross.notes.notesComponent
import ru.gross.notes.ui.detail.DetailNoteFragmentArgs
import ru.gross.notes.utils.drawableResource
import ru.gross.notes.utils.navigateUp
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: InjectableViewModelFactory

    @Inject
    lateinit var navigator: Navigator

    private val viewModel: MainViewModel by viewModels { factory }
    private val binding: ActivityMainBinding by dataBindings(R.layout.activity_main)
    private val navController by lazy(mode = LazyThreadSafetyMode.NONE) { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesComponent().inject(this)

        with(binding) {
            lifecycleOwner = this@MainActivity
            title = viewModel.title
            fab.setOnClickListener { navigator.showAddNote(this@MainActivity) }
            toolbar.setNavigationOnClickListener {
                if (onBackPressedDispatcher.hasEnabledCallbacks()) {
                    onBackPressedDispatcher.onBackPressed()
                } else {
                    navigateUp(navController)
                }
            }
            bottomBar.setNavigationOnClickListener { displayStubMessage() }
            bottomBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_share -> {
                        displayStubMessage()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.action_delete -> {
                        displayStubMessage()
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
        }

        navController.addOnDestinationChangedListener { _, destination, a ->
            when (destination.id) {
                R.id.navigation_detail_note -> {
                    binding.fab.hide()
                    binding.toolbar.navigationIcon = drawableResource(R.drawable.ic_arrow_back_24px)
                    binding.bottomBar.navigationIcon = null
                    val args = DetailNoteFragmentArgs.fromBundle(
                        a ?: return@addOnDestinationChangedListener
                    )
                    if (args.noteId != null) {
                        binding.bottomBar.replaceMenu(R.menu.detail_note_menu)
                    } else {
                        binding.bottomBar.menu.clear()
                    }
                }
                else -> {
                    binding.fab.show()
                    binding.toolbar.navigationIcon = null
                    binding.bottomBar.navigationIcon = drawableResource(R.drawable.ic_menu_24px)
                    binding.bottomBar.replaceMenu(R.menu.bottom_appbar_menu)
                }
            }
        }
    }
}