package ru.gross.notes.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.gross.notes.R
import ru.gross.notes.common.BaseFragment
import ru.gross.notes.common.handle
import ru.gross.notes.databinding.FragmentNoteDetailCardBinding
import ru.gross.notes.notesComponent
import ru.gross.notes.utils.addBackPressedCallback
import ru.gross.notes.utils.navigateUp
import javax.inject.Inject

class DetailNoteFragment :
    BaseFragment<FragmentNoteDetailCardBinding>(R.layout.fragment_note_detail_card) {
    @Inject
    lateinit var factory: NoteDetailsViewModel.Factory

    private val viewModel: NoteDetailsViewModel by viewModels { factory.setNoteId(args.noteId) }
    private val args: DetailNoteFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        notesComponent().inject(this)
        addBackPressedCallback(this, action = ::saveInternal)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.editable = true
        ViewCompat.setTransitionName(binding.rootPane, args.noteId)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.details.observe(viewLifecycleOwner) { state ->
            binding.apply {
                this.state = state
                state.handle(successHandler = ::setNote, errorHandler = ::handleError)
            }
        }
    }

    private fun saveInternal() {
        val note = binding.note ?: return
        val navController = findNavController()

        fun save() {
            viewModel.saveChanges()
            navigateUp(navController)
        }

        fun up() {
            navigateUp(navController)
        }

        val filled = note.isFilled
        if (!filled) {
            up()
            return
        }

        if (args.noteId == null && filled) {
            MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_AppTheme_Dialog)
                .setTitle(R.string.add_note_confirmation_title)
                .setCancelable(false)
                .setPositiveButton(R.string.default_confirm_btn_text) { _, _ -> save() }
                .setNegativeButton(R.string.default_negative_btn_text) { _, _ -> up() }
                .show()
        } else {
            save()
        }
    }
}