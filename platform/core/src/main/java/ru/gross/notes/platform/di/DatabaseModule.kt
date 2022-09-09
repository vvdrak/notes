package ru.gross.notes.platform.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.gross.notes.mapper.Mapper
import ru.gross.notes.platform.db.AppDatabase
import ru.gross.notes.platform.mapper.NoteEntityMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DatabaseModule {

    @Binds
    fun provideNoteEntityMapper(impl: NoteEntityMapper): Mapper<*, *>
}

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseProvider {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) =
        AppDatabase.getInstance(application, "notes.v1.dev")

    @Provides
    @Singleton
    fun provideNotesDao(database: AppDatabase) = database.notesDao()
}