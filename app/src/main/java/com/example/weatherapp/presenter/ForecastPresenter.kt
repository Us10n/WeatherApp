package com.example.weatherapp.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewItem
import com.example.weatherapp.repository.Repository
import com.example.weatherapp.view.IWeatherView

class ForecastPresenter(private var view: IWeatherView) {

    private val forecastItemsLiveData = MutableLiveData<List<ForecastRecyclerViewItem>>()
    val forecastItems: LiveData<List<ForecastRecyclerViewItem>>
        get() = forecastItemsLiveData

    fun loadWeather() {
        val homeItemsList = Repository.getWeatherForecastByCoordinate()
        forecastItemsLiveData.postValue(homeItemsList);
    }
}