package com.example.weatherapp.recycler.forecast

sealed class ForecastRecyclerViewItem{

    class Day(
        val id: Int,
        val title:String
    ):ForecastRecyclerViewItem()

    class Weather(
        val id: Int,
        val img: String,
        val time:String,
        val status: String,
        val temperature: Int
    ): ForecastRecyclerViewItem()
}
