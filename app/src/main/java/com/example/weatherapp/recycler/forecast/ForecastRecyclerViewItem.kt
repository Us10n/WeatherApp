package com.example.weatherapp.recycler.forecast

sealed class ForecastRecyclerViewItem {

    data class Day(
        val title: String
    ) : ForecastRecyclerViewItem()

    data class Weather(
        val id:Long,
        val dayPart: String,
        val img: Int,
        val time: String,
        val status: String,
        val temperature: Double
    ) : ForecastRecyclerViewItem()
}
