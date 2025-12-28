package com.osamendis.liveweather.data.api
import retrofit2.http.*
import com.osamendis.liveweather.core.AppConfig

interface AccuWeatherApi {
    @GET("locations/v1/cities/search")
    suspend fun searchCity(
        @Query("apikey") key:String=AppConfig.API_KEY,
        @Query("q") city:String
    ): List<Map<String,Any>>
}
