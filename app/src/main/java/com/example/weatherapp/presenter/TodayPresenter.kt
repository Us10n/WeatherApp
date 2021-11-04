package com.example.weatherapp.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.api.entity.CurrentWeatherItem
import com.example.weatherapp.callback.ApiCallBack
import com.example.weatherapp.converter.WeatherConverter
import com.example.weatherapp.location.DeviceLocation
import com.example.weatherapp.repository.Repository
import com.example.weatherapp.view.IWeatherView
import com.google.gson.JsonObject

class TodayPresenter(private var view: IWeatherView) : ITodayPresenter {
    override fun getLiveData(): LiveData<CurrentWeatherItem> = todayItem

    companion object Cache {
        private val todayItemLiveData = MutableLiveData<CurrentWeatherItem>()
        fun clearCache() {
            todayItemLiveData.postValue(null)
        }
    }


    private val todayItem: LiveData<CurrentWeatherItem>
        get() = todayItemLiveData

    override fun loadTodayWeather(deviceLocation: DeviceLocation) {
        if (todayItemLiveData.value != null) {
            return
        }

        Repository.getCurrentWeatherByCoordinate(
            deviceLocation.latitude,
            deviceLocation.longitude,
            object : ApiCallBack {
                override fun onCallBackToday(jsonObject: JsonObject) {
                    val todayWeather = WeatherConverter.convertJsonToCurrentWeatherItem(jsonObject)
                    todayWeather.city = deviceLocation.city
                    todayItemLiveData.postValue(todayWeather)
                    view.onLoadWeather(todayWeather)
                }

                override fun onCallBackForecast(jsonObject: JsonObject) {
                }
            })
    }
}