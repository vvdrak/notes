package ru.gross.notes.interactors

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotifyDeleteNote @Inject constructor() {
    private val _onDeleteEvent = MutableSharedFlow<Unit>(1, 0, BufferOverflow.DROP_OLDEST)
    val onDeleteEvent: Flow<Unit> = _onDeleteEvent

    suspend operator fun invoke() {
        _onDeleteEvent.emit(Unit)
    }
}