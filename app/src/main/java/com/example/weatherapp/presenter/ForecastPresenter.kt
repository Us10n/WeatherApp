package com.example.weatherapp.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.callback.ApiCallBack
import com.example.weatherapp.converter.WeatherConverter
import com.example.weatherapp.location.DeviceLocation
import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewItem
import com.example.weatherapp.repository.Repository
import com.example.weatherapp.view.IWeatherView
import com.google.gson.JsonObject
import java.util.*

class ForecastPresenter(private var view: IWeatherView) : IForecastPresenter {
    override fun getLiveData(): LiveData<List<ForecastRecyclerViewItem>> = forecastItems

    companion object Cache {
        private val forecastItemsLiveData = MutableLiveData<List<ForecastRecyclerViewItem>>()
        fun clearCache() {
            forecastItemsLiveData.postValue(ArrayList<ForecastRecyclerViewItem>())
        }
    }

    private val forecastItems: LiveData<List<ForecastRecyclerViewItem>>
        get() = forecastItemsLiveData


    override fun loadWeatherForecast(deviceLocation: DeviceLocation) {
        if (!forecastItemsLiveData.value.isNullOrEmpty()) {
            return
        }

        Repository.getWeatherForecastByCoordinate(deviceLocation.latitude, deviceLocation.longitude, object : ApiCallBack {
            override fun onCallBackForecast(jsonObject: JsonObject) {
                val weatherList = WeatherConverter.convertJsonToForecastWeatherList(jsonObject)
                val list =
                    WeatherConverter.convertForecastWeatherListToRecyclerItemList(weatherList)
                forecastItemsLiveData.postValue(list)
                view.onLoadWeather(list)
            }

            override fun onCallBackToday(jsonObject: JsonObject) {
            }
        })
    }

}