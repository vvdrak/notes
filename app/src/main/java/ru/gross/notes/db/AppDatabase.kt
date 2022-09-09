package ru.gross.notes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.gross.notes.db.conv.DateConverter
import ru.gross.notes.db.entity.NoteEntity

/**
 * Описывает базу данных приложения.
 *
 * @author gross_va.
 */
@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [DateConverter::class])
internal abstract class AppDatabase : RoomDatabase() {

    /**
     * Возвращает DAO сущности [NoteEntity]
     */
    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Создает экземпляр базы данных приложения.
         * @param context Контекст приложения.
         */
        @JvmStatic
        fun getInstance(context: Context, name: String): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context, AppDatabase::class.java, name)
                    .fallbackToDestructiveMigration()
                    .addCallback(populateItems)
                    .build()
                    .also { INSTANCE = it }
            }

        @JvmStatic
        private val populateItems = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                db.execSQL("insert into note_table (note_id, note_creation_date, note_title, note_content) values (\"3\", 1616342673, \"Сделать выводы по работе\", \"Собрать фитбэк по проекту и сделать выводы о качестве и надежности написанного кода, соответствия корпоративному стилю, а также дизайну в целом\")")
            }
        }
    }
}