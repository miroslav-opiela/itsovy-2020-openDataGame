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
            object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("RETROFIT", "On failure ${t.message}")
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("RETROFIT", response.body().toString())
                }

            }
        )
    }

}