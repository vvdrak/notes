package ru.gross.notes.interactors

import kotlinx.coroutines.CoroutineScope

/**
 * Вариант использования *Поделиться заметкой*
 *
 * @author gross_va
 */
interface ShareNote : UseCase<ShareNote.Args, Unit> {

    /**
     * Описывает аргументы варианта использования.
     */
    data class Args(
        val scope: CoroutineScope,
        val noteId: String
    )
}