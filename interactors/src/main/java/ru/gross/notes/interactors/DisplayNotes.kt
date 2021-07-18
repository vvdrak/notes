package ru.gross.notes.interactors

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.Resource
import ru.gross.notes.domain.Note
import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Вариант использования *Отображение заметок*
 *
 * @author gross_va
 */
interface DisplayNotes : UseCase<Any?, Flow<Resource<List<Note>>>>

/**
 * Описывает реализацию варианта использования *Отображение заметок*
 *
 * @author gross_va
 */
class DisplayNotesImpl @Inject constructor(
    private val repository: NotesRepository
) : DisplayNotes {
    override fun invoke(args: Any?): Flow<Resource<List<Note>>> = repository.getAll()
}