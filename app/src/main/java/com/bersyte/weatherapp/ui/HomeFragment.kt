package com.bersyte.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.FragmentHomeBinding
import com.bersyte.weatherapp.db.RecSearchFvWeatherModel
import com.bersyte.weatherapp.utils.Constants
import com.bersyte.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()
    private var searchedcCity = "Udupi, karnataka"
    var isFromSearch = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            isFromSearch = true
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
                    binding.drawerLayout.closeDrawers()
                    openFavFragment(true)
                    true
                }
                R.id.recSearch -> {
                    binding.drawerLayout.closeDrawers()
                    openFavFragment(false)
                    true

                }
                else -> false
            }
        }
    }

    private fun openFavFragment(openFromFav:Boolean){
        val fragment = RecFavFragment()
        val bundle = Bundle()
        bundle.putBoolean(Constants.OPEN_FROM_FAV,openFromFav)
        fragment.arguments = bundle
        (activity as MainActivity?)?.showCityWeather(fragment, bundle)
    }

    private fun getWeather() {
        val mString = searchedcCity.split(",").toTypedArray()
        lifecycleScope.launch {
            viewModel.getCityWeather(mString[0])
        }
        viewModel.weatherResponse.observe(viewLifecycleOwner) { weatherData ->
            binding.apply {
                //val tempInDegree=weatherData.weather[0].main-273.15
                val recFavWeatherModel = RecSearchFvWeatherModel(
                    id = 0,
                    cityName = searchedcCity,
                    cityTempInDegree = weatherData.main.temp.toString(),
                    cityTempInWords = weatherData.weather[0].description,
                    isFav = false,
                    isRecentSearch = isFromSearch
                )
                viewModel.addToRecentSearch(recFavWeatherModel)
                binding.tempIcon.setWeatherImage(weatherData.weather[0].description)
            }
        }
    }

    fun ImageView.setWeatherImage(description: String) {
        if (description.isNotEmpty()) {
            when (description) {
                "clear sky" -> {
                    this.setImageResource(R.drawable.icon_mostly_sunny_small)
                    return
                }
                "few clouds" -> {
                    this.setImageResource(R.drawable.icon_partly_cloudy_small)
                    return
                }
                "scattered clouds" -> {
                    this.setImageResource(R.drawable.icon_mostly_cloudy_small)
                    return
                }
                "shower rain" -> {
                    this.setImageResource(R.drawable.icon_rain_small)
                    return
                }
                "rain" -> {
                    this.setImageResource(R.drawable.icon_rain_small)
                    return
                }
                "thunderstorm" -> {
                    this.setImageResource(R.drawable.icon_thunderstorm_small)
                    return
                }
                "snow" -> {
                    this.setImageResource(R.drawable.icon_wind_info)
                    return
                }
                "mist" -> {
                    this.setImageResource(R.drawable.icon_wind_info)
                    return
                }
                "smoke" -> {

                    this.setImageResource(R.drawable.icon_wind_info)
                    return
                }
                else -> {
                    this.setImageResource(R.drawable.icon_mostly_sunny_small)
                    return
                }


            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding?.unbind()
    }



}