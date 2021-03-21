package ru.gross.notes.interactors

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.State
import ru.gross.notes.domain.Note
import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Вариант использования "Отображение заметок"
 *
 * @author gross_va
 */
interface DisplayNotes : UseCase<Any?, Flow<State<List<Note>?>>>

/**
 * Описывает реализацию варианта использования "Отображение заметок"
 *
 * @author gross_va
 */
class DisplayNoteImpl @Inject constructor(
    private val repository: NotesRepository
) : DisplayNotes {
    override fun invoke(args: Any?): Flow<State<List<Note>?>> = repository.getAll()
}