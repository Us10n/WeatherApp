package com.example.weatherapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapp.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val appl by lazy { (application as WeatherApplication) }
    private val LOCATION_PERMISSION_REQUEST_CODE = 101
    private lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        binding.retryButton.setOnClickListener {
            startApp()
        }
        startApp()
    }

    private fun startApp() {
        if (!checkAllPermission()) {
            if (!isLocationPermissionGranted()) getPermission()
            return
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun checkAllPermission(): Boolean =
        isInternetAvailable() && isLocationPermissionGranted() && isGpsEnabled()

    private fun isInternetAvailable(): Boolean {
        binding.internetMsg.visibility = TextView.INVISIBLE
        var isAvailable = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isAvailable = connectivityManager.activeNetwork != null
        } else {
            var isAvailable = false
            connectivityManager.allNetworkInfo.iterator().forEach {
                if (it.isAvailable) {
                    isAvailable = true
                    return@forEach
                }
            }
        }
        if (!isAvailable) {
            binding.internetMsg.visibility = TextView.VISIBLE
        }
        return isAvailable
    }

    private fun isLocationPermissionGranted(): Boolean {
        binding.permissionMsg.visibility = TextView.INVISIBLE
        val permission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!permission) {
            binding.permissionMsg.visibility = TextView.VISIBLE
        }
        return permission
    }


    private fun isGpsEnabled(): Boolean {
        binding.gpsMsg.visibility = TextView.INVISIBLE
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!status) {
            binding.gpsMsg.visibility = TextView.VISIBLE
        }
        return status
    }

    private fun getPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                val i = Intent()
                i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                i.addCategory(Intent.CATEGORY_DEFAULT)
                i.data = Uri.parse("package:$packageName")
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                startActivity(i)
            }
        }
    }
}