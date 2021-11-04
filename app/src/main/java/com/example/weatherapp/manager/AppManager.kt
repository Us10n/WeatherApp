package com.example.weatherapp.manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.weatherapp.location.CustomLocationProvider
import com.example.weatherapp.network.NetworkConnection


class AppManager(private val context: Context) {
    val networkConnection = NetworkConnection(context)
//    val locationProvider = CustomLocationProvider(context)
    var gpsEnable=true
}