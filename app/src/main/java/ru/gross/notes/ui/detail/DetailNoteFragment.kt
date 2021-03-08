package ru.gross.notes.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ru.gross.notes.R
import ru.gross.notes.common.BaseFragment
import ru.gross.notes.common.handle
import ru.gross.notes.databinding.FragmentNoteDetailCardBinding
import ru.gross.notes.notesComponent
import javax.inject.Inject

class DetailNoteFragment : BaseFragment<FragmentNoteDetailCardBinding>(R.layout.fragment_note_detail_card) {
    @Inject
    lateinit var factory: NoteDetailsViewModel.Factory
    private val viewModel: NoteDetailsViewModel by viewModels { factory.setNoteId(args.note.id) }
    private val args: DetailNoteFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        notesComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        ViewCompat.setTransitionName(binding.rootPane, args.note.id)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.details.observe(viewLifecycleOwner) { state ->
            binding.apply {
                this.state = state.apply { handle(successHandler = { note = it }) }
            }
        }
    }
}