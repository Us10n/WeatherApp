package com.example.weatherapp.presenter

import androidx.lifecycle.LiveData
import com.example.weatherapp.location.DeviceLocation
import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewItem

interface IForecastPresenter {
    fun loadWeatherForecast(deviceLocation: DeviceLocation)
    fun getLiveData(): LiveData<List<ForecastRecyclerViewItem>>
}