package com.imoon.w2s.imoon.network
import com.kvn.a.weather42.data.ForecastResult
import retrofit2.Call
import retrofit2.http.*



interface Api {

    // Cit weather
    @GET("daily?mode=json&units=metric&cnt=7")
    fun cityWeather(@Query("APPID") key: String, @Query("q") city: String): Call<ForecastResult>

    /* &APPID=15646a06818f61f7b8d7823ca833e1ce&q=600042 */
}
