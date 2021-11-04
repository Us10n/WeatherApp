package com.example.weatherapp.api.entity

data class CurrentWeatherItem(
    var city: String,
    val status:String,
    val iconId:Int,
    val cloudiness: Int,
    val direction: String,
    val humidity: Int,
    val pressure: Int,
    val wind: Int,
    val temperature:Int
) {}
