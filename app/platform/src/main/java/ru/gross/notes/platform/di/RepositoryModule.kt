package ru.gross.notes.platform.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.gross.notes.platform.repository.NotesRepositoryImpl
import ru.gross.notes.repository.NotesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun bindNotesRepository(impl: NotesRepositoryImpl): NotesRepository
}