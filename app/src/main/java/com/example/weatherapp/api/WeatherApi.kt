package com.example.weatherapp.api

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("./forecast")
    fun getWeatherForecastFiveDayThreeHour(
        @Query("lat") cityLatitude: String,
        @Query("lon") cityLongitude: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String="metric"
    ): Single<JsonObject>

    @GET("./forecast")
    fun getWeatherForecastFiveDayThreeHour(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String="metric"
    ): Single<JsonObject>

    @GET("./weather")
    fun getCurrentWeather(
        @Query("lat") cityLatitude: String,
        @Query("lon") cityLongitude: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String="metric",
    ): Single<JsonObject>

}