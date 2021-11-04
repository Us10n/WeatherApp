package com.example.weatherapp.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.*

class CustomLocationProvider(private var context: Context) : LiveData<DeviceLocation>() {
    private var fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private var locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): Boolean {
        if (!isLocationPermissionGranted()) {
            Toast.makeText(context, "Location permission isn't granted", Toast.LENGTH_LONG).show()
            return false
        }
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "Cant find gps provider", Toast.LENGTH_LONG).show()
            return false
        }
        fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener {
            if (it != null) {
                locationChangedNotify(it)
            } else {
                postValue(DeviceLocation(Double.NaN, Double.NaN, ""))
            }
        }
        return true
    }

    private fun locationChangedNotify(location: Location) {
        val geocoder = Geocoder(context, Locale.ENGLISH)
        val city =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)[0].locality
        postValue(DeviceLocation(location.latitude, location.longitude, city))
    }

    private fun isLocationPermissionGranted(): Boolean =
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
}

