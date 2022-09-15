package ru.gross.notes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.gross.notes.navigation.ComposeNavigator
import ru.gross.notes.navigation.Navigator

@Module
@InstallIn(ActivityComponent::class)
internal interface NavigatorModule {

    @Binds
    fun bindNavigator(impl: ComposeNavigator): Navigator
}