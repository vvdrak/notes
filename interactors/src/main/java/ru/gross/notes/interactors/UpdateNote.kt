package ru.gross.notes.interactors

import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Вариант использования *Обновить заметку*
 *
 * @author gross_va
 */
interface UpdateNote : UseCase<UpdateNote.Args, Unit> {

    /**
     * Описывает аргументы варианта использования.
     *
     * @author gross_va
     */
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
}

/**
 * Реализация варианта использования *Обновить заметку*
 *
 * @author gross_va
 */
class UpdateNoteImpl @Inject constructor(
    private val notesRepository: NotesRepository
) : UpdateNote {
    override fun invoke(args: UpdateNote.Args) {
        notesRepository.update(args.id, args.title, args.content)
    }
}