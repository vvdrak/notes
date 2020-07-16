package ru.gross.notes.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.gross.notes.common.State
import ru.gross.notes.model.Note
import java.util.*
import javax.inject.Inject

/**
 * Описывает репоизторий заметок.
 * @author gross_va
 */
interface NotesRepository : Repository {

    fun getById(id: String)

    /**
     * Возвращает список заметок.
     */
    fun getAll(): Flow<State<List<Note>>>
}


class NotesRepositoryImpl : NotesRepository {
    override fun getById(id: String) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<State<List<Note>>> {
        TODO("Not yet implemented")
    }
}

class NotesRepositoryStub @Inject constructor(

) : NotesRepository {
    override fun getById(id: String) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<State<List<Note>>> = flow {
        emit(
            State.Success<List<Note>>(
                arrayListOf(
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
            )
        )
    }
}