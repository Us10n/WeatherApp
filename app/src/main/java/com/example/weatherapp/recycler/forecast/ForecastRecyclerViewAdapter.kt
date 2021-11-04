package com.example.weatherapp.recycler.forecast

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DayItemBinding
import com.example.weatherapp.databinding.WeatherItemBinding

class ForecastRecyclerViewAdapter : RecyclerView.Adapter<ForecastRecyclerViewHolder>() {
    var items = listOf<ForecastRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastRecyclerViewHolder {
        return when (viewType) {
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
            is ForecastRecyclerViewHolder.WeatherViewHolder -> {
                val weather=items[position] as ForecastRecyclerViewItem.Weather
                if(weather.id==0L){
                    holder.itemView.findViewById<CardView>(R.id.forecastCardView)
                        .setCardBackgroundColor(Color.parseColor("#CFDFFB"))
                }else{
                    holder.itemView.findViewById<CardView>(R.id.forecastCardView)
                        .setCardBackgroundColor(Color.parseColor("#FFFFFFFF"))
                }
                holder.bind(weather)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ForecastRecyclerViewItem.Day -> R.layout.day_item
            is ForecastRecyclerViewItem.Weather -> R.layout.weather_item
        }
    }

    override fun getItemCount(): Int = items.size
}
