package ru.gross.notes.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.dataBindings
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.gross.mvi.MviFragment
import ru.gross.notes.R
import ru.gross.notes.databinding.FragmentDisplayNotesBinding
import ru.gross.notes.navigation.Navigator
import javax.inject.Inject

@AndroidEntryPoint
internal class DisplayNotesFragment : MviFragment<State, Effect>(R.layout.fragment_display_notes) {
    @Inject
    lateinit var notesAdapter: NotesAdapter

    @Inject
    lateinit var navigator: Navigator

    override val viewModel: NotesViewModel by viewModels()
    private val binding by dataBindings(FragmentDisplayNotesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notesPresenter.adapter = notesAdapter.apply {
            itemClickListener = { _, note ->
                viewModel.submitEvent(Event.ClickNote(note))
            }
        }
    }

    override fun onDestroyView() {
        binding.notesPresenter.adapter = null
        super.onDestroyView()
    }

    override fun handleViewEffect(effect: Effect) {
        when (effect) {
            is Effect.DisplayNote -> navigator.showNoteDetail(requireActivity(), effect.note)
        }
    }

    override fun renderViewState(state: State) {
        binding.isLoading = state is State.LoadingList
        if (state is State.DisplayList) {
            binding.notes = state.list
        }
    }
}