package com.osamendis.liveweather.data.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.osamendis.liveweather.core.AppConfig

object RetrofitClient {
    val api:AccuWeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AccuWeatherApi::class.java)
    }
}
