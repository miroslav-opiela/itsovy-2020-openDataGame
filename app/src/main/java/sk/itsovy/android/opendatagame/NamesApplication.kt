package sk.itsovy.android.opendatagame

import android.app.Application

class NamesApplication: Application() {

    val database by lazy { NamesDatabase.getDatabase(this)}
    val repository by lazy { NamesRepository(database.namesDao())}
}