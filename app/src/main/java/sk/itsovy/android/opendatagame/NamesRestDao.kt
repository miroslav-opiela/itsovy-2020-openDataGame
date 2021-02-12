package sk.itsovy.android.opendatagame

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://ics.upjs.sk/~opiela/rest/index.php/"

private val MOSHI = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val RETROFIT = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(MOSHI))
    .build()

interface NamesRestDao {

    @GET("names")
    fun getNames(): Call<List<Record>>

}

object RestApi {
    val namesRestDao: NamesRestDao by lazy {
        RETROFIT.create(NamesRestDao::class.java)
    }
}