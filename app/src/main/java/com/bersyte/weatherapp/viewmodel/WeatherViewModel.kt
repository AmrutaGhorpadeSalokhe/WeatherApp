package com.bersyte.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bersyte.weatherapp.db.RecSearchFvWeatherModel
import com.bersyte.weatherapp.model.WeatherDataModel
import com.bersyte.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(val repository: WeatherRepository) : ViewModel() {

    private val _response = MutableLiveData<WeatherDataModel>()
    val weatherResponse: LiveData<WeatherDataModel>
        get() = _response


    private val _favCity = MutableLiveData<ArrayList<RecSearchFvWeatherModel>>()
    val favResponse: LiveData<ArrayList<RecSearchFvWeatherModel>>
        get() = _favCity


    fun getCityWeather(cityName: String?) {
        getWeather(cityName!!)
    }

    private fun getWeather(cityName: String) = viewModelScope.launch {
        repository.getWeather(cityName).let { response ->

            if (response.isSuccessful) {
                _response.postValue(response.body())
            } else {
                Log.d("tag", "getWeather Error: ${response.code()}")
            }
        }
    }

    fun addToFav(recFavWeatherModel: RecSearchFvWeatherModel) = viewModelScope.launch {
        repository.addToFav(recFavWeatherModel).let { res ->

        }
    }

    fun getAllFavoriteCity() = viewModelScope.launch {
        repository.getAllFavCity(true).let { res ->
            val resList = res as ArrayList
            _favCity.postValue(resList)
        }
    }

    fun deleteFav(recWeatherRepository: RecSearchFvWeatherModel) =viewModelScope.launch {
        repository.deleteFav(recWeatherRepository)
    }

    fun deleteAllFav()=viewModelScope.launch {
        repository.deleteAllFav()
    }

    fun deleteAllRecentSearch()=viewModelScope.launch {
        repository.deleteAllRecSearch()
    }

}













