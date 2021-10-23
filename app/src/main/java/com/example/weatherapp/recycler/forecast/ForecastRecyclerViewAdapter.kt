package com.example.weatherapp.recycler.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DayItemBinding
import com.example.weatherapp.databinding.WeatherItemBinding
import kotlin.coroutines.coroutineContext
import kotlin.math.absoluteValue

class ForecastRecyclerViewAdapter : RecyclerView.Adapter<ForecastRecyclerViewHolder>() {
    var items = listOf<ForecastRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastRecyclerViewHolder {
        return when(viewType){
            R.layout.day_item -> ForecastRecyclerViewHolder.DayViewHolder(
                DayItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.weather_item -> ForecastRecyclerViewHolder.WeatherViewHolder(
                WeatherItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: ForecastRecyclerViewHolder, position: Int) {
        when (holder) {
            is ForecastRecyclerViewHolder.DayViewHolder -> holder.bind(items[position] as ForecastRecyclerViewItem.Day)
            is ForecastRecyclerViewHolder.WeatherViewHolder -> holder.bind(items[position] as ForecastRecyclerViewItem.Weather)
        }
        if(position==1){
            holder.itemView.context
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is ForecastRecyclerViewItem.Day -> R.layout.day_item
            is ForecastRecyclerViewItem.Weather -> R.layout.weather_item
        }
    }

    override fun getItemCount(): Int = items.size
}
