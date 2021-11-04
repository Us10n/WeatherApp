package com.example.weatherapp.api.entity.forecast

data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Item>,
    val message: Int
)