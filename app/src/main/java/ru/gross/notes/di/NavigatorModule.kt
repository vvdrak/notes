package ru.gross.notes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.gross.notes.navigation.Navigator
import ru.gross.notes.navigation.NavigatorImpl

@Module
@InstallIn(ActivityComponent::class)
internal interface NavigatorModule {

    @Binds
    fun bindNavigator(impl: NavigatorImpl): Navigator
}