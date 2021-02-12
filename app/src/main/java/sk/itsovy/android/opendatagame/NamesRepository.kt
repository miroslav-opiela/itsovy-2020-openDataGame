package sk.itsovy.android.opendatagame

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NamesRepository(private val namesDao: NamesDao) {

    // flow ako typ premennej pisat nemusime
    val allNames: Flow<List<Record>> = namesDao.getAllNames()

    @WorkerThread
    suspend fun insert(record: Record) {
        namesDao.insert(record)
    }

    suspend fun loadNames() {
        namesDao.deleteAll()
        val call = RestApi.namesRestDao.getNames()
        val response = suspendCoroutine<Response<List<Record>>> {
            continuation ->
            call.enqueue(
                object : Callback<List<Record>> {
                    override fun onFailure(call: Call<List<Record>>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }

                    override fun onResponse(
                        call: Call<List<Record>>,
                        response: Response<List<Record>>
                    ) {
                        continuation.resume(response)
                    }

                }
            )
        }
        val list = response.body()
        if (list != null) {
            for (record in list) {
                insert(record)
            }
        }


    }

}