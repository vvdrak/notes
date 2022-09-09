package ru.gross.notes.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import ru.gross.notes.mapper.Mapper
import ru.gross.notes.mapper.NoteDetailViewMapper
import ru.gross.notes.mapper.NoteViewListMapper
import ru.gross.notes.navigation.Navigator
import ru.gross.notes.navigation.NavigatorImpl
import ru.gross.notes.ui.list.NotesAdapter

@Module
@InstallIn(SingletonComponent::class)
internal interface MainModule {

    @Binds
    fun bindNavigator(impl: NavigatorImpl): Navigator

    @Binds
    fun bindNoteDetailMapper(impl: NoteDetailViewMapper): Mapper<*, *>

    @Binds
    fun provideNoteMapper(impl: NoteViewListMapper): Mapper<*, *>
}

@Module
@InstallIn(FragmentComponent::class)
internal object UIModule {

    @Provides
    fun provideNoteAdapter(): NotesAdapter = NotesAdapter()
}