package ru.gross.notes.interactors

import kotlinx.coroutines.flow.Flow
import ru.gross.notes.common.State
import ru.gross.notes.domain.Note
import ru.gross.notes.repository.NotesRepository
import javax.inject.Inject

/**
 * Вариант использования *Отображение подробной информации о заметке*.
 *
 * @author gross_va
 */
interface DisplayNoteDetail : UseCase<String, Flow<State<Note?>>>

/**
 * Описывает реализацию варианта использования *Отображение подробной информации о заметке*.
 *
 * @author gross_va
 */
class DisplayNoteDetailImpl @Inject constructor(
    private val repository: NotesRepository
) : DisplayNoteDetail {
    override fun invoke(args: String): Flow<State<Note?>> = repository.getById(args)
}