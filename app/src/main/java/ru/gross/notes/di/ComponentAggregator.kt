package ru.gross.notes.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import ru.gross.notes.db.AppDatabase
import ru.gross.notes.interactors.*
import ru.gross.notes.mapper.Mapper
import ru.gross.notes.mapper.NoteDetailViewMapper
import ru.gross.notes.mapper.NoteEntityMapper
import ru.gross.notes.mapper.NoteViewMapper
import ru.gross.notes.navigation.Navigator
import ru.gross.notes.navigation.NavigatorImpl
import ru.gross.notes.repository.NotesRepository
import ru.gross.notes.repository.NotesRepositoryImpl
import ru.gross.notes.ui.list.NotesAdapter
import javax.inject.Singleton

/**
 * Описывает главный модуль приложения.
 * @author gross_va
 */
@Module
@InstallIn(SingletonComponent::class)
interface MainModule {

    @Binds
    fun bindNavigator(impl: NavigatorImpl): Navigator

    @Binds
    fun bindNotesRepository(impl: NotesRepositoryImpl): NotesRepository

    @Binds
    fun bindDisplayNotes(impl: DisplayNotesImpl): DisplayNotes

    @Binds
    fun bindDisplayNoteDetail(impl: DisplayNoteDetailImpl): DisplayNoteDetail

    @Binds
    fun bindUpdateNote(impl: UpdateNoteImpl): UpdateNote

    @Binds
    fun bindShareNote(impl: ShareNoteImpl): ShareNote

    @Binds
    fun bindDeleteNote(impl: DeleteNoteImpl): DeleteNote

    @Binds
    fun bindNoteDetailMapper(impl: NoteDetailViewMapper): Mapper<*, *>

    @Binds
    fun provideNoteMapper(impl: NoteViewMapper): Mapper<*, *>

    @Binds
    fun provideNoteEntityMapper(impl: NoteEntityMapper): Mapper<*, *>
}

@Module
@InstallIn(FragmentComponent::class)
object UIModule {

    @Provides
    fun provideNoteAdapter(): NotesAdapter = NotesAdapter()
}

@Module
@InstallIn(SingletonComponent::class)
object PersistModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) =
        AppDatabase.getInstance(application, "notes.v1.dev")

    @Provides
    @Singleton
    fun provideNotesDao(database: AppDatabase) = database.notesDao()
}