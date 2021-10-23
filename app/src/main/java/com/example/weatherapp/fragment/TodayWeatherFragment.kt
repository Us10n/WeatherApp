package com.example.weatherapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentTodayWeatherBinding


class TodayWeatherFragment : Fragment() {

    lateinit var binding: FragmentTodayWeatherBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayWeatherBinding.inflate(inflater)
        return binding.root
    }
}