package sk.itsovy.android.opendatagame

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Record::class], version = 1, exportSchema = false)
abstract class NamesDatabase : RoomDatabase() {

    abstract fun namesDao(): NamesDao

    companion object {
        @Volatile
        private var INSTANCE: NamesDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NamesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NamesDatabase::class.java,
                    "names_database"
                )
                    .addCallback(NamesDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class NamesDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            Log.d("DATABASE", "ON CREATE")
            super.onCreate(db)
            // alternativne populateDatabase(it.namesDao())
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.namesDao())
                }
            }
        }

        suspend fun populateDatabase(namesDao: NamesDao) {
            Log.d("DATABASE", "populate database")
            namesDao.deleteAll()

            namesDao.insert(Record("Zdeno", 2))
            namesDao.insert(Record("Žigo", 1))
            namesDao.insert(Record("Žaneta", 2))
        }

    }
}