package com.example.weatherapp.view

import android.app.Application
import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewItem

interface IWeatherView {
    fun onLoadWeather(data: Any)
    fun getApplication():Application
}