package sk.itsovy.android.opendatagame

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "names_table")
data class Record(
    @PrimaryKey val name: String,
    val count: Int
)