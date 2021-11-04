package com.example.weatherapp.converter

import com.example.weatherapp.R
import com.example.weatherapp.api.entity.CurrentWeatherItem
import com.example.weatherapp.api.entity.current.CurrentWeather
import com.example.weatherapp.api.entity.forecast.Item
import com.example.weatherapp.recycler.forecast.ForecastRecyclerViewItem
import com.google.gson.Gson
import com.google.gson.JsonObject

object WeatherConverter {
    fun convertJsonToForecastWeatherList(jsonObject: JsonObject): ArrayList<Item> {
        val weatherArray = jsonObject.getAsJsonArray("list")

        val forecastWeatherList = ArrayList<Item>()
        for (weather in weatherArray) {
            val itemObject = Gson().fromJson(weather, Item::class.java)
            forecastWeatherList.add(itemObject)
        }
        return forecastWeatherList
    }

    fun convertJsonToCurrentWeatherItem(jsonObject: JsonObject): CurrentWeatherItem {
        val weather = Gson().fromJson(jsonObject, CurrentWeather::class.java)
        val city = ""
        val status = weather.weather[0].description
        val iconId = getIcon(weather.weather[0].icon)
        val cloudiness = weather.clouds.all
        val direction = getCompassDirection(weather.wind.deg)
        val humidity = weather.main.humidity
        val pressure = weather.main.pressure
        val wind = weather.wind.speed
        val temperature = weather.main.temp
        return CurrentWeatherItem(
            city,
            status,
            iconId,
            cloudiness,
            direction,
            humidity,
            pressure,
            wind.toInt(),
            temperature.toInt()
        )
    }

    fun convertForecastWeatherListToRecyclerItemList(weatherList: List<Item>): ArrayList<ForecastRecyclerViewItem> {
        val recyclerList = ArrayList<ForecastRecyclerViewItem>()
        recyclerList.add(ForecastRecyclerViewItem.Day("Today"))
        var currentDay =
            DateConvertor.findDayOfWeekInt(DateConvertor.findDate(weatherList[0].dt_txt))
        for ((id, weather) in weatherList.withIndex()) {
            val day = DateConvertor.findDayOfWeekInt(DateConvertor.findDate(weather.dt_txt))
            if (day != currentDay) {
                recyclerList.add(ForecastRecyclerViewItem.Day(DateConvertor.getDayOfWeekString(day)))
                currentDay = day
            }
            val dayIcon = weather.weather[0].icon
            val dayPart = dayIcon.substring(dayIcon.length - 1)
            val icon = getIcon(dayIcon)
            val time = DateConvertor.findTime(weather.dt_txt)?.substring(0, 5)
            val weatherStatus = weather.weather[0].description
            val temperature = Math.round(weather.main.temp * 10).toDouble() / 10
            recyclerList.add(
                ForecastRecyclerViewItem.Weather(
                    id.toLong(),
                    dayPart,
                    icon,
                    time!!,
                    weatherStatus,
                    temperature
                )
            )
        }
        return recyclerList
    }

    private fun getCompassDirection(angle: Int): String {
        val tmp = ((angle / 22.5) + 0.5).toInt()
        val arr = arrayOf(
            "N",
            "NNE",
            "NE",
            "ENE",
            "E",
            "ESE",
            "SE",
            "SSE",
            "S",
            "SSW",
            "SW",
            "WSW",
            "W",
            "WNW",
            "NW",
            "NNW"
        )
        return arr[(tmp % 16)]
    }

    private fun getIcon(iconId: String): Int {
        val id = when (iconId) {
            "01d" -> R.drawable.day01
            "01n" -> R.drawable.night01
            "02d" -> R.drawable.day02
            "02n" -> R.drawable.night02
            "03d", "03n", "04d", "04n" -> R.drawable.day_night_03_04
            "09d", "09n" -> R.drawable.day_night_09
            "10d" -> R.drawable.day10
            "10n" -> R.drawable.night10
            "11d", "11n" -> R.drawable.day_night_11
            "13d", "13n" -> R.drawable.day_night_13
            "50d", "50n" -> R.drawable.day_night_50
            else -> -1
        }
        return id

    }


}