package ru.gross.notes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ru.gross.notes.ui.list.NotesAdapter

@Module
@InstallIn(FragmentComponent::class)
internal object UIModule {

    @Provides
    fun provideNoteAdapter(): NotesAdapter = NotesAdapter()
}