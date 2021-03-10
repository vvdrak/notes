package ru.gross.notes.ui

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.gross.notes.R

private object StubUI {

    @JvmStatic
    fun showStubMessage(context: Context) {
        Toast.makeText(context, R.string.feature_not_available_text, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.displayStubMessage() =
    StubUI.showStubMessage(requireContext())


fun Activity.displayStubMessage() =
    StubUI.showStubMessage(this)