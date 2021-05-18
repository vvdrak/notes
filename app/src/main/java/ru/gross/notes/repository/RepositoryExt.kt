package ru.gross.notes.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.gross.notes.common.Resource

/**
 * Возвращает [Flow] для обращения к удаленному ресурсу.
 * Содержит все обработчики, в т.ч. при возникновении исключения.
 *
 * @param delay Задержка [Resource.Loading].
 * @param block Функция приостановки для получения данных.
 * @param T Тип данных.
 * @author vva2@gelicon.biz
 */
fun <T> Repository.resourceFlow(
    delay: Long = 500L,
    block: suspend FlowCollector<Resource<T>>.() -> Unit
): Flow<Resource<T>> {
    return flow(block)
        .onStart { emit(Resource.Loading(delay)) }
        .catch {
            Log.e(this@resourceFlow::class.simpleName, "Handle error", it)
            emit(Resource.Error(it.message))
        }
        .flowOn(Dispatchers.IO)
}