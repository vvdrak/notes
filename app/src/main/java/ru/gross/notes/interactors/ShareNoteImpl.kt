package ru.gross.notes.interactors

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import ru.gross.notes.R
import ru.gross.notes.common.onSuccess
import ru.gross.notes.domain.Note
import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

private const val TEXT_TYPE = "text/plain"

/**
 * Описывает реализацию варианта использования *Поделиться заметкой*
 *
 * @author gross_va
 * @see ShareNote
 */
internal class ShareNoteImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notesRepository: NotesRepository
) : ShareNote {
    override fun invoke(args: ShareNote.Args) {
        notesRepository.getById(args.noteId)
            .onSuccess {
                val text = it.sharingContent(context)
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = TEXT_TYPE
                }
                context.startActivity(
                    Intent.createChooser(sendIntent, null)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
            .launchIn(args.scope)
    }

    private fun Note.sharingContent(context: Context): String {
        val title = title ?: ""
        val content = content ?: ""
        return context.getString(R.string.share_note_mask, title, content).trim()
    }
}