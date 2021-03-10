package ru.gross.notes

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.gross.notes.di.DaggerNotesComponent
import ru.gross.notes.di.NotesComponent

class NotesApp : Application() {
    val appComponent: NotesComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerNotesComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}

/**
 * Возвращает главный компонент системы.
 */
fun Fragment.notesComponent(): NotesComponent =
    (requireActivity().application as NotesApp).appComponent

/**
 * Возвращает главный компонент системы.
 */
fun Activity.notesComponent(): NotesComponent =
    (application as NotesApp).appComponent