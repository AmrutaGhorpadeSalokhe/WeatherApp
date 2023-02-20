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

    private val _recFavWeatherModel = MutableLiveData<RecSearchFvWeatherModel>()
    val recFavWeatherModel: LiveData<RecSearchFvWeatherModel>
        get() = _recFavWeatherModel

    var recWeatherModelTemp = MutableLiveData<RecSearchFvWeatherModel>()

    var _isOpenFromFav = MutableLiveData<Boolean>()

    val isOpenFromFav: LiveData<Boolean>
        get() = _isOpenFromFav

    val showProgressBarUserInfo: MutableLiveData<Boolean> = MutableLiveData()

    var cityListSize: MutableLiveData<Int> = MutableLiveData()

    init {
        showProgressBarUserInfo.value = true
    }

    fun setFav(openFromFav: Boolean) {
        _isOpenFromFav.postValue(openFromFav)
    }

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

    fun addRemoveFav(recFavWeatherModel: RecSearchFvWeatherModel, isFavSearch: Boolean) =
        viewModelScope.launch {
            repository.update(recFavWeatherModel).let {
                getAllFavoriteCity(isFavSearch)
            }
        }

    fun addRemoveFromFav(recFavWeatherModel: RecSearchFvWeatherModel) = viewModelScope.launch {
        if (!recFavWeatherModel.isFav!!) {
            repository.updateFavFlag(true, recFavWeatherModel.id)
        } else {
            repository.updateFavFlag(false, recFavWeatherModel.id)
        }
        repository.getFavModel(recFavWeatherModel.id).let { res ->
            _recFavWeatherModel.postValue(res)
        }
    }

    fun addToRecentSearch(recFavWeatherModel: RecSearchFvWeatherModel) =
        viewModelScope.launch {
            repository.addToRecentSearch(recFavWeatherModel).let {
                repository.checkItemPresent(recFavWeatherModel.cityName!!).let { res ->
                    _recFavWeatherModel.postValue(res)
                    showProgressBarUserInfo.value = false
                }
            }
        }


    fun getAllFavoriteCity(isFavSearch: Boolean) = viewModelScope.launch {
        var resList = ArrayList<RecSearchFvWeatherModel>()

        if (isFavSearch) {
            repository.getAllFavCity(true).let { res ->
                resList = res as ArrayList
                _favCity.postValue(resList)
            }
        } else {
            repository.getAllRecentSearch(true).let { res ->
                resList = res as ArrayList
                _favCity.postValue(resList)
            }
        }
        if (resList.isNotEmpty()) {
            cityListSize.postValue(resList.size)
        }
    }

    fun deleteAllFav()=viewModelScope.launch {
        repository.deleteAllFav()
    }

    fun deleteAllRecentSearch()=viewModelScope.launch {
        repository.deleteAllRecSearch()
    }

}













