package ru.gross.notes.interactors

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotifyShareNote @Inject constructor() {
    private val _onShareEvent = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    val onShareEvent: Flow<Unit> = _onShareEvent

    suspend operator fun invoke() {
        _onShareEvent.emit(Unit)
    }
}