package ru.gross.notes.common

/**
 * Универсальный класс описывающий состояние.
 * @param T Тип значения.
 * @author gross_va
 */
sealed class State<out T> {

    /**
     * Состояние загрузки. Параметр [delay] указывает на время ожидания, после которого следует отобразить индикатор загрузки.
     * Это позволяет избежать мерцания индикатора, если сама загрузка прошла быстрее указанного времени.
     * @param delay Настраиваемое время ожидания в мс.
     */
    data class Loading(val delay: Long = 500L) : State<Nothing>()

    /**
     * Состояние успешности.
     * @param data Данные.
     * @param T Тип данных.
     */
    data class Success<out T>(val data: T?) : State<T>()

    /**
     * Состояние ошибки.
     * @param message Сообщение с текстом ошибки.
     */
    data class Error(val message: String?) : State<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$message]"
        }
    }
}

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
inline fun <I, O> State<I>.map(crossinline transform: (I?) -> O?): State<O> = when (this) {
    is State.Loading -> this
    is State.Success -> State.Success(transform(data))
    is State.Error -> this
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