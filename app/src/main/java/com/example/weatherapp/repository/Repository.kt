package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.callback.ApiCallBack
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {
    private const val apiKey = "ebd6f86c615c06d3606d3579f5dba716"

    private val api = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(WeatherApi::class.java)


    fun getCurrentWeatherByCoordinate(
        latitude: Double,
        longitude: Double,
        callBack: ApiCallBack
    ) {
        api.getCurrentWeather(latitude.toString(), longitude.toString(), apiKey)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callBack.onCallBackToday(it)
            }, {
                Log.d("mine", "json error", it)
            })
    }

    fun getWeatherForecastByCoordinate(latitude: Double, longitude: Double, callBack: ApiCallBack) {
        api.getWeatherForecastFiveDayThreeHour(
            latitude.toString(),
            longitude.toString(),
            apiKey,
            "metric"
        )
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callBack.onCallBackForecast(it)
            }, {
                Log.d("mine", "json error", it)
            })
    }
}