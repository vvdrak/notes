package ru.gross.notes.ui.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import ru.gross.notes.R
import ru.gross.notes.common.BaseFragment
import ru.gross.notes.common.handle
import ru.gross.notes.databinding.FragmentDisplayNotesBinding
import ru.gross.notes.di.InjectableViewModelFactory
import ru.gross.notes.notesComponent
import ru.gross.notes.ui.MainViewModel
import javax.inject.Inject

class DisplayNotesFragment : BaseFragment<FragmentDisplayNotesBinding>(R.layout.fragment_display_notes) {
    @Inject
    lateinit var factory: InjectableViewModelFactory
    private val viewModel: MainViewModel by activityViewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        notesComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.notesPresenter.adapter = NotesAdapter().apply {
            itemClickListener = { view, note -> note?.let { viewModel.setAsCurrent(view, it) } }
        }

        viewModel.notes.observe(viewLifecycleOwner, Observer { state ->
            binding.apply { state.handle(successHandler = { notes = it }) }
        })
    }
}