package com.example.weatherapp.fragment

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.weatherapp.MainActivity
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.api.entity.CurrentWeatherItem
import com.example.weatherapp.databinding.FragmentTodayWeatherBinding
import com.example.weatherapp.location.CustomLocationProvider
import com.example.weatherapp.presenter.ITodayPresenter
import com.example.weatherapp.presenter.TodayPresenter
import com.example.weatherapp.view.IWeatherView


class TodayWeatherFragment : Fragment(), IWeatherView {
    private lateinit var binding: FragmentTodayWeatherBinding
    private lateinit var presenter: ITodayPresenter
    private val application by lazy { ((activity as MainActivity).getMyApplication() as WeatherApplication) }
    private var gpsEnable = true;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayWeatherBinding.inflate(inflater)
        presenter = TodayPresenter(this)
        val locationProvider = CustomLocationProvider(requireContext())

        //gps observer
        locationProvider.observe(viewLifecycleOwner, Observer { location ->
            if (!location.isEmpty() && application.appManager.networkConnection.isNetworkAvailable() && gpsEnable)
                presenter.loadTodayWeather(location)
        })
        //swipe to refresh observer
        presenter.getLiveData().observe(viewLifecycleOwner, Observer {
            if (it == null) {
                binding.shareView.visibility = TextView.GONE
                if (application.appManager.networkConnection.isNetworkAvailable()) {
                    gpsEnable = locationProvider.getCurrentLocation()
                } else {
                    Toast.makeText(requireContext(), "Turn on the internet", Toast.LENGTH_LONG)
                        .show()
                }
            }

        })
        val networkConnection = application.appManager.networkConnection
        networkConnection.observe(viewLifecycleOwner, Observer {
            if (it) {
                gpsEnable = locationProvider.getCurrentLocation()
            }
        })

        binding.shareView.setOnClickListener {
            if (networkConnection.isNetworkAvailable() && presenter.getLiveData().value != null) {
                shareWeather()
            } else {
                Toast.makeText(requireContext(), "Can't send message", Toast.LENGTH_LONG).show()
            }
        }
        if (presenter.getLiveData().value != null) {
            setData(presenter.getLiveData().value!!)
        }

        return binding.root
    }

    private fun shareWeather() {
        val weather = presenter.getLiveData().value
        val messageBuilder = StringBuilder()
        messageBuilder.append("My current weather:\n")
        messageBuilder.append("City: ${weather?.city}\n")
        messageBuilder.append("Status: ${weather?.temperature}°| ${weather?.status}\n")
        messageBuilder.append("Humidity: ${weather?.humidity} %\n")
        messageBuilder.append("Cloudiness: ${weather?.cloudiness} %\n")
        messageBuilder.append("Pressure: ${weather?.pressure} hPa\n")
        messageBuilder.append("Wind speed: ${weather?.wind} m/s\n")
        messageBuilder.append("Wind direction: ${weather?.direction}")

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, messageBuilder.toString())
        val chosenIntent = Intent.createChooser(intent, "Choose source")
        startActivity(chosenIntent)
    }

    override fun onLoadWeather(todayWeather: Any) {
        setData(todayWeather as CurrentWeatherItem)
    }

    private fun setData(todayWeather: CurrentWeatherItem) {
        binding.currentWeatherImage.setImageResource(todayWeather.iconId)
        binding.currentWeatherCityName.text = todayWeather.city
        binding.currentWeatherStatus.text = todayWeather.temperature.toString() + "°| " + todayWeather.status
        binding.cloudiness.text = todayWeather.cloudiness.toString() + " %"
        binding.direction.text = todayWeather.direction
        binding.humidity.text = todayWeather.humidity.toString() + " %"
        binding.pressure.text = todayWeather.pressure.toString() + " hPa"
        binding.wind.text = todayWeather.wind.toString() + " m/s"
        binding.shareView.visibility = TextView.VISIBLE
    }

    override fun getApplication(): Application = application
}