package ru.gross.notes.interactors

import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Реализация варианта использования *Обновить заметку*
 *
 * @author gross_va
 */
class UpdateNote @Inject constructor(
    private val notesRepository: NotesRepository
) {
    data class Args(
        /**
         * Идентификатор заметки.
         */
        val id: String?,

        /**
         * Заголовок заметки.
         */
        val title: String?,

        /**
         * Контент заметки.
         */
        val content: String?
    )

    suspend operator fun invoke(args: Args) {
        notesRepository.update(args.id, args.title, args.content)
    }
}