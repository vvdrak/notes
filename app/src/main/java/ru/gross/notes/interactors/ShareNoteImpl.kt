package ru.gross.notes.interactors

import android.app.Application
import android.content.Context
import kotlinx.coroutines.flow.launchIn
import ru.gross.notes.R
import ru.gross.notes.common.onSuccess
import ru.gross.notes.domain.Note
import ru.gross.notes.repository.NotesRepository
import ru.gross.notes.utils.ShareUtils
import ru.gross.notes.utils.stringResource
import javax.inject.Inject

/**
 * Описывает реализацию варианта использования *Поделиться заметкой*
 *
 * @author gross_va
 * @see ShareNote
 */
class ShareNoteImpl @Inject constructor(
    private val app: Application,
    private val notesRepository: NotesRepository
) : ShareNote {
    override fun invoke(args: ShareNote.Args) {
        val context = app
        notesRepository.getById(args.noteId)
            .onSuccess {
                val text = it.sharingContent(context)
                ShareUtils.shareText(context, text)
            }
            .launchIn(args.scope)
    }

    private fun Note.sharingContent(context: Context): String {
        val title = title ?: ""
        val content = content ?: ""
        return context.stringResource(R.string.share_note_mask, title, content).trim()
    }
}