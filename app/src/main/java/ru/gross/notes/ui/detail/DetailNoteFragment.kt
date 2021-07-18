package ru.gross.notes.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ru.gross.notes.R
import ru.gross.notes.common.BaseFragment
import ru.gross.notes.common.handle
import ru.gross.notes.databinding.FragmentNoteDetailCardBinding
import ru.gross.notes.utils.addBackPressedCallback
import ru.gross.notes.utils.navigateUp
import javax.inject.Inject

@AndroidEntryPoint
class DetailNoteFragment :
    BaseFragment<FragmentNoteDetailCardBinding>(R.layout.fragment_note_detail_card) {
    @Inject
    lateinit var factory: NoteDetailsViewModel.Factory.AssistedFactory

    private val viewModel: NoteDetailsViewModel by viewModels {
        factory.create(args.noteId)
    }

    private val args: DetailNoteFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
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

    override fun share() {
        viewModel.shareIt()
    }

    override fun delete() {
        MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_AppTheme_Dialog)
            .setTitle(R.string.delete_note_confirmation_title)
            .setPositiveButton(R.string.default_confirm_btn_text) { _, _ -> deleteInternal() }
            .setNegativeButton(R.string.default_negative_btn_text) { _, _ -> }
            .show()
    }

    private fun deleteInternal() {
        viewModel.deleteIt()
        navigateUp()
    }

    private fun saveInternal() {
        val note = binding.note ?: return

        fun save() {
            viewModel.saveChanges()
            navigateUp()
        }

        fun up() {
            navigateUp()
        }

        val filled = note.isFilled
        if (!filled) {
            up()
            return
        }

        if (args.noteId == null && filled) {
            MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_AppTheme_Dialog)
                .setTitle(R.string.add_note_confirmation_title)
                .setPositiveButton(R.string.default_confirm_btn_text) { _, _ -> save() }
                .setNegativeButton(R.string.default_negative_btn_text) { _, _ -> up() }
                .show()
        } else {
            save()
        }
    }
}