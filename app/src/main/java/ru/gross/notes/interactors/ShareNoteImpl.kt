package ru.gross.notes.interactors

import android.content.Context
import ru.gross.notes.domain.Note
import ru.gross.notes.navigation.Navigator
import javax.inject.Inject

/**
 * Описывает реализацию варианта использования *Поделиться заметкой*
 *
 * @author gross_va
 * @see ShareNote
 */
class ShareNoteImpl @Inject constructor(
    private val context: Context,
    private val navigator: Navigator
) : ShareNote {
    override fun invoke(args: Note) {
        navigator.shareText(context, args.content.toString())
    }
}