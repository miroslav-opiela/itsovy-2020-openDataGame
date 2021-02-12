package sk.itsovy.android.opendatagame

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NamesRepository(private val namesDao: NamesDao) {

    // flow ako typ premennej pisat nemusime
    val allNames: Flow<List<Record>> = namesDao.getAllNames()

    @WorkerThread
    suspend fun insert(record: Record) {
        namesDao.insert(record)
    }

    suspend fun loadNames() {
        RestApi.namesRestDao.getNames().enqueue(
            object: Callback<List<Record>> {
                override fun onFailure(call: Call<List<Record>>, t: Throwable) {
                    Log.d("RETROFIT", "On failure ${t.message}")
                }

                override fun onResponse(call: Call<List<Record>>, response: Response<List<Record>>) {
                    Log.d("RETROFIT", "${response.body()?.size}")
                    Log.d("RETROFIT", response.body().toString())
                }

            }
        )
    }

}