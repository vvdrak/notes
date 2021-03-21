@file:JvmName("State")

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

    /**
     * Возвращает флаг, указывающий на то, что текущее состояние -- успех.
     */
    fun isSuccess(): Boolean = this is Success

    /**
     * Возвращает флаг, указывающий на то, что текущее состояние -- загрузка.
     */
    fun isLoading(): Boolean = this is Loading

    /**
     * Возвращает флаг, указывающий на то, что текущее состояние -- ошибка.
     */
    fun isError(): Boolean = this is Error

    companion object {

        @JvmStatic
        fun <T> loading(): State<T> = Loading()

        @JvmStatic
        fun <T> error(message: String?): State<T> = Error(message)

        @JvmStatic
        fun <T> success(data: T): State<T> = Success(data)
    }
}