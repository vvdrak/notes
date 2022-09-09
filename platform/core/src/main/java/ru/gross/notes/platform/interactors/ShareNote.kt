package ru.gross.notes.platform.interactors

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.gross.notes.common.handle
import ru.gross.notes.domain.Note
import ru.gross.notes.platform.R
import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

private const val TEXT_TYPE = "text/plain"

/**
 * Описывает реализацию варианта использования *Поделиться заметкой*
 *
 * @author gross_va
 * @see ShareNote
 */
class ShareNote @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notesRepository: NotesRepository
) {

    suspend operator fun invoke(noteId: String) {
        notesRepository.getById(noteId)
            .handle(successHandler = {
                val text = it.sharingContent(context)
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = TEXT_TYPE
                }
                context.startActivity(
                    Intent.createChooser(sendIntent, null)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            })
    }

    private fun Note.sharingContent(context: Context): String {
        val title = title ?: ""
        val content = content ?: ""
        return context.getString(R.string.share_note_mask, title, content).trim()
    }
}