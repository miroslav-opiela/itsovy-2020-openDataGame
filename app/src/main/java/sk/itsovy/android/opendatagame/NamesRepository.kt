package sk.itsovy.android.opendatagame

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class NamesRepository(private val namesDao: NamesDao) {

    // flow ako typ premennej pisat nemusime
    val allNames: Flow<List<Record>> = namesDao.getAllNames()

    @WorkerThread
    suspend fun insert(record: Record) {
        namesDao.insert(record)
    }

}