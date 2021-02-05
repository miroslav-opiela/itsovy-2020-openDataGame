package sk.itsovy.android.opendatagame

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NamesApplication: Application() {

    val appScope = CoroutineScope(SupervisorJob())

    val database by lazy { NamesDatabase.getDatabase(this, appScope)}
    val repository by lazy { NamesRepository(database.namesDao())}
}