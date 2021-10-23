package com.example.weatherapp.repository

import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewItem

object Repository {
    fun getCurrentWeatherByCoordinate() { //todo

    }

    fun getWeatherForecastByCoordinate():List<ForecastRecyclerViewItem>{ //todo
        val homeItemsList = mutableListOf<ForecastRecyclerViewItem>()
        homeItemsList.add(ForecastRecyclerViewItem.Day(0, "Today"))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(0, "", "13:00", "Clear", 22))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(1, "", "16:00", "Rain", 18))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(2, "", "19:00", "Rain", 15))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(3, "", "22:00", "Clouds", 12))
        homeItemsList.add(ForecastRecyclerViewItem.Day(0, "Friday"))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(0, "", "13:00", "Clear", 22))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(1, "", "16:00", "Rain", 18))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(2, "", "19:00", "Rain", 15))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(3, "", "22:00", "Clouds", 12))
        homeItemsList.add(ForecastRecyclerViewItem.Day(0, "Friday"))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(0, "", "13:00", "Clear", 22))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(1, "", "16:00", "Rain", 18))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(2, "", "19:00", "Rain", 15))
        homeItemsList.add(ForecastRecyclerViewItem.Weather(3, "", "22:00", "Clouds", 12))
        return homeItemsList
    }
}