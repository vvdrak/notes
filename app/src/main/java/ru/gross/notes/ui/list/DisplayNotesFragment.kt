package ru.gross.notes.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.gross.notes.R
import ru.gross.notes.common.BaseFragment
import ru.gross.notes.common.handle
import ru.gross.notes.databinding.FragmentDisplayNotesBinding
import ru.gross.notes.navigation.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class DisplayNotesFragment :
    BaseFragment<FragmentDisplayNotesBinding>(R.layout.fragment_display_notes) {
    @Inject
    lateinit var notesAdapter: NotesAdapter

    @Inject
    lateinit var navigator: Navigator

    private val viewModel: NotesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.notesPresenter.adapter = notesAdapter.apply {
            itemClickListener = NoteClickListener(navigator::showNoteDetail)
        }

        viewModel.notes.observe(viewLifecycleOwner) { state ->
            binding.apply {
                this.state = state
                state.handle(successHandler = ::setNotes, errorHandler = ::handleError)
            }
        }
    }

    override fun onDestroyView() {
        binding.notesPresenter.adapter = null
        super.onDestroyView()
    }
}

@Suppress("FunctionName")
private fun Fragment.NoteClickListener(
    actionSupplier: (FragmentActivity, View, NoteView) -> Unit
): (View, NoteView?) -> Unit = { view, note ->
    note?.let { actionSupplier(requireActivity(), view, it) }
}