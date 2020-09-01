package ru.gross.notes.common

import android.view.View

/**
 * Описывает навигационное событие.
 * @author gross_va
 */
data class NavMove<out T>(
    /**
     * Объект, связанный с перемещением.
     */
    val item: T,
    /**
     * Объект, инициирующий навигационное перемещение.
     */
    val view: View
)