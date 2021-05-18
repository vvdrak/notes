package ru.gross.notes.repository

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import ru.gross.notes.common.Resource

/**
 * Возвращает [Flow] для обращения к ресурсу.
 * Содержит все обработчики, в т.ч. при возникновении исключения.
 *
 * @param delay Задержка [Resource.Loading].
 * @param block Функция приостановки для получения данных.
 * @param T Тип данных.
 * @author gross_va
 */
fun <T> Repository.resourceFlow(
    delay: Long = 500L,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend FlowCollector<Resource<T>>.() -> Unit,
): Flow<Resource<T>> {
    return flow(block)
        .onStart { emit(Resource.Loading(delay)) }
        .catch {
            Log.e(this@resourceFlow::class.simpleName, "Handle error", it)
            emit(Resource.Error(it.message))
        }
        .flowOn(ioDispatcher)
}

/**
 * Выполняет действие [block] в теле корутины, блокируя поток, связанный с [ioDispatcher]
 *
 * @author gross_va
 */
fun <T> Repository.execute(
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend CoroutineScope.() -> T,
): T {
    return kotlin.runCatching {
        runBlocking(ioDispatcher, block)
    }.onFailure {
        Log.e(this::class.simpleName, "Handle error", it)
    }.getOrThrow()
}