package ru.gross.notes.interactors

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.State
import ru.gross.notes.model.Note
import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Описывает вариант использования *Отображение подробной информации о заметке*.
 * @author gross_va
 */
class DisplayNoteDetail @Inject constructor(
    private val repository: NotesRepository
) : UseCase<String, Flow<State<Note?>>> {
    override fun invoke(args: String): Flow<State<Note?>> = repository.getById(args)
}