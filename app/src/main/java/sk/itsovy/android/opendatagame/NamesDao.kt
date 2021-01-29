package sk.itsovy.android.opendatagame

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NamesDao {

    @Query("SELECT * FROM names_table")
    fun getAllNames() : Flow<List<Record>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record: Record)

    @Query("DELETE FROM names_table")
    suspend fun deleteAll()

}