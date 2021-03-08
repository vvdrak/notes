package ru.gross.notes.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <T, R> Flow<State<T>>.mapState(crossinline transform: (value: T?) -> R) =
    map { it.map(transform) }