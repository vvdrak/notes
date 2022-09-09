package ru.gross.notes.interactors

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

interface NotifyDeleteNote : UseCase<Unit, Unit> {
    val onDeleteEvent: Flow<Unit>
}

class NotifyDeleteNoteImpl @Inject constructor() : NotifyDeleteNote {
    private val _onDeleteEvent = MutableSharedFlow<Unit>(1, 0, BufferOverflow.DROP_OLDEST)
    override val onDeleteEvent: Flow<Unit> = _onDeleteEvent

    override fun invoke(args: Unit) {
        _onDeleteEvent.tryEmit(Unit)
    }
}