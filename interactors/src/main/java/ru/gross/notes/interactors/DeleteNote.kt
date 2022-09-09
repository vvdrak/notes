package ru.gross.notes.interactors

import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Описывает реализацию варианта использования *Удалить заметку*
 *
 * @author gross_va
 * @see DeleteNote
 */
class DeleteNote @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke(key: String) {
        notesRepository.remove(key)
    }
}