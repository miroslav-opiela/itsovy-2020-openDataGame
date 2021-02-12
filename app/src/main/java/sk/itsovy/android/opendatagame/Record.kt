package sk.itsovy.android.opendatagame

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "names_table")
data class Record(
    @Json(name = "Krstné_meno") @PrimaryKey val name: String,
    @Json(name = "Počet") val count: Int
)