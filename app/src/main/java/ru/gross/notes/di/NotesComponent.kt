package ru.gross.notes.di

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.*
import dagger.multibindings.IntoMap
import ru.gross.notes.interactors.DisplayNotes
import ru.gross.notes.interactors.UseCase
import ru.gross.notes.repository.NotesRepository
import ru.gross.notes.repository.NotesRepositoryStub
import ru.gross.notes.ui.MainActivity
import ru.gross.notes.ui.MainViewModel
import ru.gross.notes.ui.detail.DetailNoteFragment
import ru.gross.notes.ui.list.DisplayNotesFragment
import ru.gross.notes.ui.list.NotesAdapter
import javax.inject.Singleton

/**
 * Описывает главный компонент приложения.
 * @author gross_va
 */
@Singleton
@Component(modules = [MainModule::class, UIModule::class])
interface NotesComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): NotesComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: DisplayNotesFragment)

    fun inject(fragment: DetailNoteFragment)
}

/**
 * Описывает главный модуль приложения.
 * @author gross_va
 */
@Module(includes = [ViewModelFactoryModule::class])
interface MainModule {

    @Binds
    fun bindNotesRepository(repositoryImpl: NotesRepositoryStub): NotesRepository

    @Binds
    fun bindDisplayNotes(useCase: DisplayNotes): UseCase<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}

@Module
object UIModule {

    @JvmStatic
    @Provides
    fun provideNoteAdapter(): NotesAdapter = NotesAdapter()
}