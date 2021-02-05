package sk.itsovy.android.opendatagame

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Record::class], version = 1, exportSchema = false)
abstract class NamesDatabase : RoomDatabase() {

    abstract fun namesDao(): NamesDao

    companion object {
        @Volatile
        private var INSTANCE: NamesDatabase? = null

        fun getDatabase(context: Context) : NamesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NamesDatabase::class.java,
                    "names_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}