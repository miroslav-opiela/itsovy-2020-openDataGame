package sk.itsovy.android.opendatagame

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://ics.upjs.sk/~opiela/rest/index.php/"

private val RETROFIT = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

interface NamesRestDao {

    @GET("names")
    fun getNames(): Call<String>

}

object RestApi {
    val namesRestDao: NamesRestDao by lazy {
        RETROFIT.create(NamesRestDao::class.java)
    }
}