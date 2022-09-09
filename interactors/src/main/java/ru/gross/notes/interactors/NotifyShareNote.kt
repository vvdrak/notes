package ru.gross.notes.interactors

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NotifyShareNote : UseCase<Unit, Unit> {
    val onShareEvent: Flow<Unit>
}

class NotifyShareNoteImpl @Inject constructor() : NotifyShareNote {
    private val _onShareEvent = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    override val onShareEvent: Flow<Unit> = _onShareEvent

    override fun invoke(args: Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            _onShareEvent.emit(Unit)
        }
    }
}