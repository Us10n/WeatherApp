package com.example.weatherapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentTodayWeatherBinding
import com.example.weatherapp.view.IWeatherView


class TodayWeatherFragment : Fragment() , IWeatherView{

    lateinit var binding: FragmentTodayWeatherBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun loadWeather() {
        TODO("Not yet implemented")
    }
}