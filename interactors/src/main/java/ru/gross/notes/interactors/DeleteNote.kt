package ru.gross.notes.interactors

import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Вариант использования *Удалить заметку*
 *
 * @author gross_va
 */
interface DeleteNote : UseCase<String, Unit>

/**
 * Описывает реализацию варианта использования *Удалить заметку*
 *
 * @author gross_va
 * @see DeleteNote
 */
class DeleteNoteImpl @Inject constructor(
    private val notesRepository: NotesRepository
) : DeleteNote {
    override fun invoke(args: String) {
        notesRepository.remove(args)
    }
}