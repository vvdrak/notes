package ru.gross.notes.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import ru.gross.notes.R
import ru.gross.notes.common.BaseFragment
import ru.gross.notes.databinding.FragmentNoteDetailCardBinding
import ru.gross.notes.di.InjectableViewModelFactory
import ru.gross.notes.notesComponent
import ru.gross.notes.ui.MainViewModel
import javax.inject.Inject

class DetailNoteFragment : BaseFragment<FragmentNoteDetailCardBinding>(R.layout.fragment_note_detail_card) {
    @Inject
    lateinit var factory: InjectableViewModelFactory
    private val viewModel: MainViewModel by activityViewModels { factory }
    private val args: DetailNoteFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        notesComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val transition = MaterialContainerTransform().apply {
//            scrimColor = Color.WHITE
////            isDrawDebugEnabled = true
//            //duration = 4000
//            pathMotion = MaterialArcMotion()
////            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
////            fitMode = MaterialContainerTransform.FIT_MODE_HEIGHT
//        }
//        sharedElementEnterTransition = transition
//        sharedElementReturnTransition = transition
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
        with(binding) {
            note = args.note
        }
    }
}