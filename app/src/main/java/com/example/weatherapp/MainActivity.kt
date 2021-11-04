package com.example.weatherapp

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.fragment.ForecastFragment
import com.example.weatherapp.fragment.TodayWeatherFragment
import com.example.weatherapp.presenter.ForecastPresenter
import com.example.weatherapp.presenter.TodayPresenter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val forecastFragment = ForecastFragment()
    private val todayFragment = TodayWeatherFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            openFragment(todayFragment)
        }

        binding.refreshLayout.setOnRefreshListener {
            ForecastPresenter.clearCache()
            TodayPresenter.clearCache()
            binding.refreshLayout.isRefreshing = false
        }

        configBottomNavigationView()
    }

    private fun configBottomNavigationView() {
        binding.bottomNavigationView.menu.forEach {
            val view = binding.bottomNavigationView.findViewById<View>(it.itemId)
            view.setOnLongClickListener {
                true
            }
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.todayWeather -> {
                    if (binding.bottomNavigationView.selectedItemId != R.id.todayWeather)
                        openFragment(todayFragment)
                }
                R.id.forecastWeather -> {
                    if (binding.bottomNavigationView.selectedItemId != R.id.forecastWeather)
                        openFragment(forecastFragment)
                }
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fContainerView.id, fragment).commit()
    }

    fun getMyApplication(): Application? {
        return application
    }

}