package ru.gross.notes.ui

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.gross.notes.R

internal object StubUI {

    @JvmStatic
    fun showStubMessage(view: View) {
        val message = view.context.getString(R.string.feature_not_available_text)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
            anchorView = view
        }.show()
    }
}