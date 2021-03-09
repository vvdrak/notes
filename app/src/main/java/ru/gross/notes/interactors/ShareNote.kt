package ru.gross.notes.interactors

import android.content.Context
import ru.gross.notes.data.model.Note
import ru.gross.notes.navigation.Navigator
import javax.inject.Inject

/**
 * Описывает вариант использования *Поделиться заметкой*
 * @author gross_va
 */
class ShareNote @Inject constructor(
    private val context: Context,
    private val navigator: Navigator
) : UseCase<Note, Unit> {
    override fun invoke(args: Note) {
        navigator.shareText(context, args.content.toString())
    }
}