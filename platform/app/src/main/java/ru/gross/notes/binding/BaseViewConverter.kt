@file:JvmName("BaseViewConverter")

package ru.gross.notes.binding

import android.view.View

fun otherwiseGone(condition: Boolean): Int {
    return if (condition) View.VISIBLE else View.GONE
}

fun otherwiseVisible(condition: Boolean): Int {
    return if (condition) View.GONE else View.VISIBLE
}

fun isNotNullOrEmpty(content: CharSequence?): Boolean {
    return content.isNullOrEmpty().not()
}