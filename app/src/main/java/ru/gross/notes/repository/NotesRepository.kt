package ru.gross.notes.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.gross.notes.common.State
import ru.gross.notes.common.asState
import ru.gross.notes.model.Note
import java.util.*
import javax.inject.Inject

/**
 * Описывает репоизторий заметок.
 * @author gross_va
 */
interface NotesRepository : Repository {

    /**
     * Возвращает заметку [Note] по ее идентификатору.
     * @param id Идентификтаор заметки.
     */
    fun getById(id: String): Flow<State<Note?>>

    /**
     * Возвращает список заметок.
     */
    fun getAll(): Flow<State<List<Note>>>
}

/**
 * Реализация репозитория заметок.
 * @author gross_va
 */
class NotesRepositoryImpl : NotesRepository {
    override fun getById(id: String): Flow<State<Note?>> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<State<List<Note>>> {
        TODO("Not yet implemented")
    }
}

/**
 * Заглушка репозитория заметок.
 * @author gross_va
 */
class NotesRepositoryStub @Inject constructor() : NotesRepository {
    override fun getById(id: String): Flow<State<Note?>> =
        flowOf(data.firstOrNull { it.id == id }.asState())

    override fun getAll(): Flow<State<List<Note>>> =
        flowOf(data.asState())

    companion object Store {
        @JvmField
        val data = arrayListOf(
            Note(
                "1",
                Date(),
                "Проснуться утром в 6:00",
                "Проснуться утром и сделать зарядку"
            ),
            Note("2", Date(), "Купи продуктов", "Хлеб, Молоко и фрукты"),
            Note(
                "3",
                Date(),
                "Сделать выводы по работе",
                "Собрать фитбэк по проекту и сделать выводы о качестве и надежности написанного кода, соответствия корпоративному стилю, а также дизайну в целом"
            )
        )
    }
}