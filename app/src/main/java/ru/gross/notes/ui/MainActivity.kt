package ru.gross.notes.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import ru.gross.notes.R
import ru.gross.notes.common.EventObserver
import ru.gross.notes.databinding.ActivityMainBinding
import ru.gross.notes.di.InjectableViewModelFactory
import ru.gross.notes.notesComponent
import ru.gross.notes.ui.list.DisplayNotesFragmentDirections
import ru.gross.notes.utils.navigate
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: InjectableViewModelFactory
    private val viewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesComponent().inject(this)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        with(binding) {
            fab.setOnClickListener { viewModel.setAsCurrent(it, null) }
            bottomBar.setNavigationOnClickListener { }
        }

        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_detail_note -> {
                    binding.bottomBar.navigationIcon = null
                    binding.fab.hide()
                    binding.bottomBar.replaceMenu(R.menu.detail_note_menu)
                }
                else -> {
                    binding.fab.show()
                    binding.bottomBar.setNavigationIcon(R.drawable.ic_menu_24px)
                    binding.bottomBar.replaceMenu(R.menu.bottom_appbar_menu)
                }
            }
        }

        viewModel.current.observe(this, EventObserver {
            val transitionName = ViewCompat.getTransitionName(it.view)
            val extras = if (transitionName != null) FragmentNavigatorExtras(it.view to transitionName) else null
            navigate(
                navController,
                DisplayNotesFragmentDirections.toNoteCard(it.item),
                navExtras = extras
            )
        })
    }
}