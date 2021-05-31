package ru.gross.notes.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.*
import dagger.multibindings.IntoMap
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
import ru.gross.notes.ui.MainActivity
import ru.gross.notes.ui.MainViewModel
import ru.gross.notes.ui.detail.DetailNoteFragment
import ru.gross.notes.ui.detail.NoteDetailsViewModel
import ru.gross.notes.ui.list.DisplayNotesFragment
import ru.gross.notes.ui.list.NotesAdapter
import ru.gross.notes.ui.list.NotesViewModel
import javax.inject.Singleton

/**
 * Описывает главный компонент приложения.
 * @author gross_va
 */
@Singleton
@Component(modules = [NotesComponent.PersistModule::class, NotesComponent.MainModule::class, NotesComponent.UIModule::class])
interface NotesComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): NotesComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: DisplayNotesFragment)

    fun inject(fragment: DetailNoteFragment)

    /**
     * Описывает главный модуль приложения.
     * @author gross_va
     */
    @Module(includes = [ViewModelFactoryModule::class])
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

        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        fun bindMainViewModel(impl: MainViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(NotesViewModel::class)
        fun bindNotesViewModel(impl: NotesViewModel): ViewModel

        @Binds
        fun bindNoteDetailsViewModelFactory(impl: NoteDetailsViewModel.Factory): ViewModelProvider.Factory
    }

    @Module
    object UIModule {

        @Provides
        fun provideNoteAdapter(): NotesAdapter = NotesAdapter()
    }

    @Module
    object PersistModule {

        @Provides
        @Singleton
        fun provideDatabase(application: Application) =
            AppDatabase.getInstance(application, "notes.v1.dev")

        @Provides
        @Singleton
        fun provideNotesDao(database: AppDatabase) = database.notesDao()
    }
}