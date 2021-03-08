package ru.gross.notes.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.*
import dagger.multibindings.IntoMap
import ru.gross.notes.interactors.DisplayNoteDetail
import ru.gross.notes.interactors.DisplayNotes
import ru.gross.notes.interactors.ShareNote
import ru.gross.notes.interactors.UseCase
import ru.gross.notes.mapper.Mapper
import ru.gross.notes.mapper.NoteDetailMapper
import ru.gross.notes.mapper.NoteMapper
import ru.gross.notes.navigation.Navigator
import ru.gross.notes.navigation.NavigatorImpl
import ru.gross.notes.repository.NotesRepository
import ru.gross.notes.repository.NotesRepositoryStub
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
@Component(modules = [NotesComponent.MainModule::class, NotesComponent.UIModule::class])
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
        fun bindNotesRepository(impl: NotesRepositoryStub): NotesRepository

        @Binds
        fun bindDisplayNotes(impl: DisplayNotes): UseCase<*, *>

        @Binds
        fun bindDisplayNoteDetail(impl: DisplayNoteDetail): UseCase<*, *>

        @Binds
        fun bindShareNote(impl: ShareNote): UseCase<*, *>

        @Binds
        fun bindNoteDetailMapper(impl: NoteDetailMapper): Mapper<*, *>

        @Binds
        fun provideNoteMapper(impl: NoteMapper): Mapper<*, *>

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

        @JvmStatic
        @Provides
        fun provideNoteAdapter(): NotesAdapter = NotesAdapter()
    }
}