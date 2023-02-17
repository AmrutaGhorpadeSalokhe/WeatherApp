package com.bersyte.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bersyte.weatherapp.databinding.ActivityHomeBinding
import com.bersyte.weatherapp.viewmodel.WeatherViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
    }
    private fun observeData(){
        viewModel.weatherResponse.observe(this) { weatherData ->

            binding.apply {

            }
            /*  binding.apply {
                  tvCityName.text = "Luanda"
                  tvDescription.text = weather.description
                  tvTemperature.text = weather.temperature
                  tvWind.text = weather.wind

                  val forecast = weather.forecast
                  tvForecast1.text = "${forecast[0].temperature}/ ${forecast[0].wind}"
                  tvForecast2.text = "${forecast[1].temperature}/ ${forecast[1].wind}"
                  tvForecast3.text = "${forecast[2].temperature}/ ${forecast[2].wind}"

              }*/

        }
    }
}