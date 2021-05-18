package ru.gross.notes.db.conv

import androidx.room.TypeConverter
import java.util.*

/**
 * Описывает конвертер даты для `Room Persistence Library`
 *
 * @author gross_va
 */
object DateConverter {
    @JvmStatic
    @TypeConverter
    fun toDate(timestamp: Long?): Date? = timestamp?.let { Date(it) }

    @JvmStatic
    @TypeConverter
    fun toTimestamp(date: Date?): Long? = date?.time
}
