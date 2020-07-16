package ru.gross.notes.common

import androidx.lifecycle.Observer

/**
 * Используется в качестве оболочки для данных, представляющие событие.
 */
class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Возвращает содержимое и предотвращает его повторное использование.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Возвращает содержимое, даже если оно уже обработано.
     */
    fun peekContent(): T = content
}

/**
 * [Observer] для [Event]s, упрощающий щаблон проверки для его содержимого.
 *
 * [onEventUnhandledContent] вызывается *только*, если содержимое [Event] не было обработано.
 */
class EventObserver<T>(
    private val onEventUnhandledContent: (T) -> Unit
) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { onEventUnhandledContent(it) }
    }
}