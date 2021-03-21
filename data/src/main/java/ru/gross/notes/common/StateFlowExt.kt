package ru.gross.notes.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <T, R> Flow<State<T>>.mapState(crossinline transform: (value: T?) -> R) =
    map { it.map(transform) }

/**
 * Возвращает значение экземпляра [State]
 * @author gross_va
 */
val <T> State<T>.value: T?
    get() = when (this) {
        is State.Success -> this.data
        else -> null
    }

/**
 * Создает экземпляр [State] со значением взятым из источника [T]
 * @author gross_va
 */
fun <T> T.asState(): State<T> = when (this) {
    is Throwable -> State.Error(message)
    else -> State.Success(this)
}

/**
 * Выполняет преобразование данных.
 * @param transform Функция преобразования из [I] в [O].
 * @param I Исходный тип данных.
 * @param O Требуемый тип данных.
 * @author gross_va
 */
inline fun <I, O> State<I>.map(crossinline transform: (I?) -> O): State<O> = when (this) {
    is State.Loading -> this
    is State.Success -> State.Success(transform(data))
    is State.Error -> this
}

/**
 * Заменяет экземпляр [State].
 * @param stateSupplier Функция замены экземпляра.
 * @param I Исходный тип данных.
 * @param O Требуемый тип данных.
 * @author gross_va
 */
inline fun <I, O> State<I>.switchMap(crossinline stateSupplier: (I?) -> State<O>): State<O> {
    return when (this) {
        is State.Loading -> this
        is State.Success -> stateSupplier(data)
        is State.Error -> this
    }
}

/**
 * Выполняет обработку данных.
 * @param successHandler Метод, выполняющийся в случае успешной загрузки данных.
 * @param loadingHandler Метод, выполняющийся в случае начала загрузки данных.
 * @param errorHandler Метод, выполняющийся в случае ошибки загрузки данных.
 * @param T Тип данных [State]
 * @author gross_va
 */
inline fun <T> State<T>.handle(
    successHandler: (T?) -> Unit,
    noinline loadingHandler: (() -> Unit)? = null,
    noinline errorHandler: ((String?) -> Unit)? = null
) {
    when (this) {
        is State.Loading -> loadingHandler?.invoke()
        is State.Success -> successHandler(data)
        is State.Error -> errorHandler?.invoke(message)
    }
}