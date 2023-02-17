package com.bersyte.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.FragmentHomeBinding
import com.bersyte.weatherapp.db.RecSearchFavWeatherModel
import com.bersyte.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()
    private var searchedcCity = "Kolhapur"
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_home, container, false)
        binding.weatheViewModel = viewModel
        binding.lifecycleOwner = this
        initUI()
        getWeather()
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            //get city details
            searchedcCity = bundle.getString("city").toString()
        }
    }

    private fun initUI() {
        binding.searchButton.setOnClickListener {
            (activity as MainActivity?)?.showFragment(SearchFragment(), true)
        }

        val toggle = ActionBarDrawerToggle(
            activity, binding.drawerLayout, binding.toolBar, R.string.nav_open,
            R.string.nav_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {

                    true
                }
                R.id.favCity -> {
                    (activity as MainActivity?)?.showFragment(FavouriteFragment(), true)
                    true
                }
                R.id.recSearch -> {

                    true

                }
                else -> false
            }
        }
    }

    private fun getWeather() {
        viewModel.getCityWeather(searchedcCity)
        viewModel.weatherResponse.observe(viewLifecycleOwner) { weatherData ->
            binding.apply {
                val recFavWeatherModel=RecSearchFavWeatherModel(
                    id,
                    cityName = weatherData.name,
                    cityTempInDegree = weatherData.weather[0].main,
                    cityTempInWords = weatherData.weather[0].description,
                    isFav = true,
                    isRecentSearch = true
                )
                binding.model=recFavWeatherModel

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