package ru.gross.notes.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Выполняет преобразование данных в текущем [Flow] изменяя их и в ресурсе.
 */
inline fun <T, R> Flow<Resource<T>>.mapResourceFlow(crossinline transform: (value: T?) -> R) =
    map { it.map(transform) }

/**
 * Выполняет действие [action] в текущем [Flow] в случае, если экземпляр ресурса - [Resource.Success]
 */
inline fun <T> Flow<Resource<T>>.onSuccess(crossinline action: (T?) -> Unit) =
    onEach { it.handle(successHandler = action) }

/**
 * Возвращает значение экземпляра [Resource]
 * @author gross_va
 */
val <T> Resource<T>.value: T?
    get() = when (this) {
        is Resource.Success -> this.data
        else -> null
    }

/**
 * Создает экземпляр [Resource] со значением взятым из источника [T]
 * @author gross_va
 */
fun <T> T.asResource(): Resource<T> = when (this) {
    is Throwable -> Resource.Error(message)
    else -> Resource.Success(this)
}

/**
 * Выполняет преобразование данных ресурса.
 * @param transform Функция преобразования из [I] в [O].
 * @param I Исходный тип данных.
 * @param O Требуемый тип данных.
 * @author gross_va
 */
inline fun <I, O> Resource<I>.map(crossinline transform: (I?) -> O): Resource<O> = when (this) {
    is Resource.Loading -> this
    is Resource.Success -> Resource.Success(transform(data))
    is Resource.Error -> this
}

/**
 * Заменяет экземпляр [Resource].
 * @param resourceSupplier Функция замены экземпляра.
 * @param I Исходный тип данных.
 * @param O Требуемый тип данных.
 * @author gross_va
 */
inline fun <I, O> Resource<I>.switchMap(crossinline resourceSupplier: (I?) -> Resource<O>): Resource<O> {
    return when (this) {
        is Resource.Loading -> this
        is Resource.Success -> resourceSupplier(data)
        is Resource.Error -> this
    }
}

/**
 * Выполняет обработку данных ресурса.
 * @param successHandler Метод, выполняющийся в случае успешной загрузки данных.
 * @param loadingHandler Метод, выполняющийся в случае начала загрузки данных.
 * @param errorHandler Метод, выполняющийся в случае ошибки загрузки данных.
 * @param T Тип данных [Resource]
 * @author gross_va
 */
inline fun <T> Resource<T>.handle(
    successHandler: (T?) -> Unit,
    noinline loadingHandler: (() -> Unit)? = null,
    noinline errorHandler: ((String?) -> Unit)? = null
) {
    when (this) {
        is Resource.Loading -> loadingHandler?.invoke()
        is Resource.Success -> successHandler(data)
        is Resource.Error -> errorHandler?.invoke(message)
    }
}