package com.example.weatherapp.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.MainActivity
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.databinding.FragmentForecastBinding
import com.example.weatherapp.location.CustomLocationProvider
import com.example.weatherapp.presenter.ForecastPresenter
import com.example.weatherapp.presenter.IForecastPresenter
import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewAdapter
import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewItem
import com.example.weatherapp.view.IWeatherView


class ForecastFragment : Fragment(), IWeatherView {

    private lateinit var binding: FragmentForecastBinding
    private val recyclerAdapter = ForecastRecyclerViewAdapter()
    private lateinit var presenter: IForecastPresenter
    private val application by lazy { ((activity as MainActivity).getMyApplication() as WeatherApplication) }
    private var gpsEnable = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(inflater)
        presenter = ForecastPresenter(this)
        val locationProvider=CustomLocationProvider(requireContext())

        //Recycler config
        binding.forecastRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ForecastFragment.context)
            adapter = recyclerAdapter
        }
        //gps observer
        locationProvider.observe(viewLifecycleOwner, Observer {
            if (!it.isEmpty() && application.appManager.networkConnection.isNetworkAvailable() && gpsEnable) {
                binding.toolbarTitle.text = it.city
                presenter.loadWeatherForecast(it)
            }
        })
        //swipe to refresh observer
        presenter.getLiveData().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                if (application.appManager.networkConnection.isNetworkAvailable()) {
                    gpsEnable = locationProvider.getCurrentLocation()
                } else {
                    Toast.makeText(requireContext(), "Turn on the internet", Toast.LENGTH_LONG)
                        .show()
                }
            }

        })
        application.appManager.networkConnection.observe(viewLifecycleOwner, Observer {
            if (it) {
                gpsEnable = locationProvider.getCurrentLocation()
            }
        })

        return binding.root
    }

    override fun onLoadWeather(forecastItems: Any) {
        recyclerAdapter.items = forecastItems as List<ForecastRecyclerViewItem>
    }

    override fun getApplication(): Application = application
}