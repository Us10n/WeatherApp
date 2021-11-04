package com.example.weatherapp.location

data class DeviceLocation(val latitude: Double, val longitude: Double, val city: String) {
    fun isEmpty(): Boolean = latitude.isNaN() && longitude.isNaN() && city.equals("")
}
