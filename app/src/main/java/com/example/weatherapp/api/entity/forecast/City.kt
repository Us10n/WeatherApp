package com.example.weatherapp.api.entity.forecast

data class City(
    val coord: Coord,
    val country: String,
    val id: Long,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)