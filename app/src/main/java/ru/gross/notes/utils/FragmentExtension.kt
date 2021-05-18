package ru.gross.notes.utils

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

/**
 * Возвращает [OnBackPressedDispatcher], позволяющий перехватить навигацию назад.
 */
val Fragment.onBackPressedDispatcher: OnBackPressedDispatcher
    get() = requireActivity().onBackPressedDispatcher

fun Fragment.addBackPressedCallback(
    owner: LifecycleOwner? = null,
    enabled: Boolean = true,
    action: () -> Unit
) {
    onBackPressedDispatcher.addCallback(owner, enabled) {
        action()
    }
}