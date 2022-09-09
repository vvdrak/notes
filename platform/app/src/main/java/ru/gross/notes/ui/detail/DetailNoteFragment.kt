package ru.gross.notes.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.dataBindings
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ru.gross.mvi.MviFragment
import ru.gross.notes.R
import ru.gross.notes.databinding.FragmentNoteDetailCardBinding
import ru.gross.notes.utils.addBackPressedCallback
import ru.gross.notes.utils.navigateUp
import javax.inject.Inject

@AndroidEntryPoint
internal class DetailNoteFragment : MviFragment<State, Effect>(R.layout.fragment_note_detail_card) {
    private val args: DetailNoteFragmentArgs by navArgs()
    private val binding by dataBindings(FragmentNoteDetailCardBinding::bind)

    @Inject
    lateinit var factory: NoteDetailsViewModel.Factory.AssistedFactory

    override val viewModel: NoteDetailsViewModel by viewModels {
        factory.create(args.noteId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addBackPressedCallback(this) {
            viewModel.submitEvent(Event.SaveChanges(false))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editable = true
    }

    override fun handleViewEffect(effect: Effect) {
        when (effect) {
            Effect.DisplayDeleteDialog -> {
                MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_AppTheme_Dialog)
                    .setTitle(R.string.delete_note_confirmation_title)
                    .setPositiveButton(R.string.default_confirm_btn_text) { _, _ ->
                        viewModel.submitEvent(Event.DeleteNote)
                        navigateUp()
                    }
                    .setNegativeButton(R.string.default_negative_btn_text) { _, _ -> }
                    .show()
            }
            Effect.DisplaySaveDialog -> {
                MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_AppTheme_Dialog)
                    .setTitle(R.string.add_note_confirmation_title)
                    .setPositiveButton(R.string.default_confirm_btn_text) { _, _ ->
                        viewModel.submitEvent(Event.SaveChanges(true))
                    }
                    .setNegativeButton(R.string.default_negative_btn_text) { _, _ -> navigateUp() }
                    .show()
            }
            Effect.GoBack -> navigateUp()
        }
    }

    override fun renderViewState(state: State) {
        binding.isLoading = state is State.LoadDetail
        if (state is State.DisplayDetail) {
            binding.note = state.detail
        }
    }
}