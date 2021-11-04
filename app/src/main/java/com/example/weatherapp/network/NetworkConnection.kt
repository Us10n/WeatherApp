package com.example.weatherapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class NetworkConnection(private val context: Context) : LiveData<Boolean>() {

    private val validNetworkConnections: ArrayList<Network> = ArrayList()
    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback
    private var isAvailable = true

    fun isNetworkAvailable(): Boolean = isAvailable

    private fun announceStatus() {
        if (validNetworkConnections.isNotEmpty()) {
            isAvailable = true
            postValue(true)
        } else {
            isAvailable = false
            postValue(false)
        }
    }

    private fun getConnectivityManagerCallback() =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                if (validNetworkConnections.isEmpty())
                    startInternetConnectionCheck(network)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                validNetworkConnections.remove(network)
                Toast.makeText(context, "Internet connection lost", Toast.LENGTH_LONG).show()
                announceStatus()
            }
        }

    private fun startInternetConnectionCheck(network: Network) {
        val networkCapability = connectivityManager.getNetworkCapabilities(network)
        val hasNetworkConnection =
            networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
        if (hasNetworkConnection) {
            determineInternetAccess(network)
        }
    }

    private fun determineInternetAccess(network: Network) {
        val dispose = dataSource()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it && !validNetworkConnections.contains(network)) {
                    validNetworkConnections.add(network)
                    announceStatus()
                }
            }, {
                Log.e("Tag", "determinateInternetAccess: datasource error")
            })
    }

    private fun dataSource(): Single<Boolean> {
        return Single.create { subscriber ->
            val checkResult = InternetAvailability.check()
            subscriber.onSuccess(checkResult)
        }
    }

    override fun onActive() {
        super.onActive()
        connectivityManagerCallback = getConnectivityManagerCallback()
        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

}


