package ru.gross.notes.interactors

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.State
import ru.gross.notes.model.Note
import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Вариант использования "Отображение заметок"
 * @author gross_va
 */
class DisplayNotes @Inject constructor(
    private val repository: NotesRepository
) : UseCase<Any?, Flow<State<List<Note>?>>> {
    override fun invoke(args: Any?): Flow<State<List<Note>?>> = repository.getAll()
}