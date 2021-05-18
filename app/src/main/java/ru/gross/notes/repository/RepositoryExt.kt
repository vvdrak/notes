package ru.gross.notes.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.gross.notes.common.State

/**
 * Возвращает [Flow] для обращения к удаленному ресурсу.
 * Содержит все обработчики, в т.ч. при возникновении исключения.
 *
 * @param delay Задержка [State.Loading].
 * @param block Функция приостановки для получения данных.
 * @param T Тип данных.
 * @author vva2@gelicon.biz
 */
fun <T> Repository.stateFlow(
    delay: Long = 500L,
    block: suspend FlowCollector<State<T>>.() -> Unit
): Flow<State<T>> {
    return flow(block)
        .onStart { emit(State.Loading(delay)) }
        .catch {
            Log.e(this@stateFlow::class.simpleName, "Handle error", it)
            emit(State.Error(it.message))
        }
        .flowOn(Dispatchers.IO)
}