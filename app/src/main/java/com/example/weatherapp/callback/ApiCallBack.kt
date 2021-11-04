package com.example.weatherapp.callback

import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewItem
import com.google.gson.JsonObject

interface ApiCallBack {
    fun onCallBackForecast(jsonObject: JsonObject)
    fun onCallBackToday(jsonObject: JsonObject)
}