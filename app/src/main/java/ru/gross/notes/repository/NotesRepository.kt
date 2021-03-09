package ru.gross.notes.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import ru.gross.notes.data.common.State
import ru.gross.notes.data.common.asState
import ru.gross.notes.data.model.Note
import ru.gross.notes.data.repository.NotesRepository
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Реализация репозитория заметок.
 * @author gross_va
 */
class NotesRepositoryImpl : NotesRepository {
    override fun getById(id: String): Flow<State<Note?>> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<State<List<Note>?>> {
        TODO("Not yet implemented")
    }
}

/**
 * Заглушка репозитория заметок.
 * @author gross_va
 */
class NotesRepositoryStub @Inject constructor() : NotesRepository {
    override fun getById(id: String): Flow<State<Note?>> = flow {
        emit(State.Loading())
        val value = withTimeoutInternal { data.firstOrNull { it.id == id }.asState() }
        emit(value)
    }

    override fun getAll(): Flow<State<List<Note>?>> = flow {
        emit(State.Loading())
        val value = withTimeoutInternal { data.asState() }
        emit(value)
    }

    /**
     * Имитирует задержку при обращении к ресурсу.
     * Генерирует случайное число из [MIN_NETWORK_DELAY]..[MAX_NETWORK_DELAY] являющееся временем ожидания ответа.
     * По истечению данного времени возвращается ответ.
     * В случае, если сгенерированное время ожидания привысило [TIMEOUT_MS], то будет выброшено исключение (имитация IOException)
     */
    private suspend fun <T> withTimeoutInternal(block: suspend CoroutineScope.() -> T): T {
        val timeMillis = (MIN_NETWORK_DELAY..MAX_NETWORK_DELAY).random().toLong()
        return withTimeout(TIMEOUT_MS) {
            delay(timeMillis)
            block(this)
        }
    }

    companion object NotesProvider {
        @JvmStatic
        private val MIN_NETWORK_DELAY = TimeUnit.MILLISECONDS.toMillis(100L).toInt()

        @JvmStatic
        private val MAX_NETWORK_DELAY = TimeUnit.SECONDS.toMillis(1L).toInt()

        @JvmStatic
        private val TIMEOUT_MS = TimeUnit.SECONDS.toMillis(MAX_NETWORK_DELAY - 1L)

        @JvmField
        val data = arrayListOf(
            Note("2", Date(), "Список продуктов", "Хлеб, Молоко и фрукты"),
            Note(
                "3",
                Date(),
                "Сделать выводы по работе",
                "Собрать фитбэк по проекту и сделать выводы о качестве и надежности написанного кода, соответствия корпоративному стилю, а также дизайну в целом"
            )
        )
    }
}