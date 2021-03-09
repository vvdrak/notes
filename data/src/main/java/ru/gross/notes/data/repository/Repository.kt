package ru.gross.notes.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.gross.notes.data.common.State

/**
 * Маркер репозитория.
 */
interface Repository

/**
 * Возвращает [Flow] для обращения к удаленному ресурсу.
 * Содержит все обработчики, в т.ч. при возникновении исключения.
 *
 * @param delay Задержка [State.Loading].
 * @param block Функция приостановки для получения данных.
 * @param T Тип данных.
 * @author vva2@gelicon.biz
 */
@ExperimentalCoroutinesApi
fun <T> Repository.remoteFlow(
    delay: Long = 500L,
    block: suspend FlowCollector<State<T>>.() -> Unit
): Flow<State<T>> {
    return flow(block)
        .onStart { emit(State.Loading(delay)) }
        .catch {
            Log.e(this@remoteFlow::class.simpleName, "Handle error", it)
            emit(State.Error(it.message))
        }
        .flowOn(Dispatchers.IO)
}