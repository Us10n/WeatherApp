package com.example.weatherapp.recycler.forecast

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DayItemBinding
import com.example.weatherapp.databinding.WeatherItemBinding

sealed class ForecastRecyclerViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class DayViewHolder(private val binding: DayItemBinding) : ForecastRecyclerViewHolder(binding) {
        fun bind(day: ForecastRecyclerViewItem.Day) {
            binding.dayTitle.text = day.title;
        }
    }

    class WeatherViewHolder(private val binding: WeatherItemBinding) :
        ForecastRecyclerViewHolder(binding) {
        fun bind(weather: ForecastRecyclerViewItem.Weather) {
            binding.forecastWeatherImg.setImageResource(weather.img)
            binding.forecastWeatherTime.text = weather.time
            binding.forecastWeatherStatus.text = weather.status
            binding.forecastWeatherTemperature.text = weather.temperature.toString()+"°"
        }
    }
}
