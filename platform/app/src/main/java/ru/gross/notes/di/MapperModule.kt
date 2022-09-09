package ru.gross.notes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ru.gross.notes.mapper.Mapper
import ru.gross.notes.mapper.NoteDetailViewMapper
import ru.gross.notes.mapper.NoteViewListMapper

@Module
@InstallIn(FragmentComponent::class)
internal interface MapperModule {

    @Binds
    fun bindNoteDetailMapper(impl: NoteDetailViewMapper): Mapper<*, *>

    @Binds
    fun provideNoteMapper(impl: NoteViewListMapper): Mapper<*, *>
}