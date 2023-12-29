package api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers ("X-Api-Key:aLDB1AJR04dh/4IMeZFU0Q==MgOAICN5QRmaeZr0")
    @GET("logo")
    fun getAgents(
        @Query("name") name:String
    ): Call<List<ResponseItem>>
}