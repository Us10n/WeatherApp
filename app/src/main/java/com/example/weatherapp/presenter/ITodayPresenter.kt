package com.example.weatherapp.presenter

import androidx.lifecycle.LiveData
import com.example.weatherapp.api.entity.CurrentWeatherItem
import com.example.weatherapp.location.DeviceLocation
import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewItem

interface ITodayPresenter {
    fun loadTodayWeather(deviceLocation: DeviceLocation)
    fun getLiveData(): LiveData<CurrentWeatherItem>

}