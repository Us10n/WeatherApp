package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.manager.AppManager

class WeatherApplication : Application() {
    val appManager by lazy { AppManager(baseContext) }
}