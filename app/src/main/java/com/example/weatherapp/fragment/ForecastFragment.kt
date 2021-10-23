package com.example.weatherapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.databinding.FragmentForecastBinding
import com.example.weatherapp.presenter.ForecastPresenter
import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewAdapter
import com.example.weatherapp.view.IWeatherView


class ForecastFragment : Fragment(), IWeatherView {

    lateinit var binding: FragmentForecastBinding
    private val recyclerAdapter = ForecastRecyclerViewAdapter()
    private lateinit var presenter: ForecastPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(inflater)

        presenter = ForecastPresenter(this)

        binding.forecastRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ForecastFragment.context)
            adapter = recyclerAdapter
        }

        presenter.forecastItems.observe(viewLifecycleOwner,
            Observer { _ ->
                recyclerAdapter.items = presenter.forecastItems.value!!
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadWeather()
    }

    override fun loadWeather() {
        presenter.loadWeather()
    }
}