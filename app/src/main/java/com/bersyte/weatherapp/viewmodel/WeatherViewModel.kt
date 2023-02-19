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

    var _isOpenFromFav=MutableLiveData<Boolean>()
    val isOpenFromFav:LiveData<Boolean>
    get() = _isOpenFromFav

    fun setFav(openFromFav:Boolean){
        _isOpenFromFav.postValue(openFromFav)
    }


    fun getCityWeather(cityName: String?) {
        getWeather(cityName!!)
    }

    private fun getWeather(cityName: String) = viewModelScope.launch {
        repository.getWeather(cityName).let { response ->

            if (response.isSuccessful) {
                _response.postValue(response.body())
                //check if item present in room and theN Get from local
                repository.checkItemPresent(response.body()!!.name).let { res ->

                        val tempObj = RecSearchFvWeatherModel(
                            id = 0,
                            cityName = response.body()!!.name,
                            cityTempInDegree = response.body()!!.weather[0].main,
                            cityTempInWords = response.body()!!.weather[0].description,
                            isFav = true,
                            isRecentSearch = true
                        )
                    if(res!=null) {
                        _recFavWeatherModel.postValue(res)
                    }else{
                        _recFavWeatherModel.postValue(tempObj)
                    }

                    //recWeatherModelTemp.postValue(tempObj)

                }
            } else {
                Log.d("tag", "getWeather Error: ${response.code()}")
            }
        }
    }

    fun addRemoveFav(recFavWeatherModel: RecSearchFvWeatherModel) = viewModelScope.launch {
        if (!recFavWeatherModel.isFav!!) {
            repository.addToFav(recFavWeatherModel)
        } else {
            repository.removeFromFav(false, recFavWeatherModel.id)
        }
        repository.getFavModel(recFavWeatherModel.id).let { res ->
            _recFavWeatherModel.postValue(res)
        }

    }

    fun getAllFavoriteCity(isFavSearch:Boolean) = viewModelScope.launch {
        if(isFavSearch) {
            repository.getAllFavCity(true).let { res ->
                val resList = res as ArrayList
                _favCity.postValue(resList)
            }
        }else{
            repository.getAllRecentSearch(true).let { res ->
                val resList = res as ArrayList
                _favCity.postValue(resList)
            }
        }
    }

    /*fun deleteFav(recWeatherRepository: RecSearchFvWeatherModel) =viewModelScope.launch {
        repository.(recWeatherRepository)
    }*/

    fun deleteAllFav()=viewModelScope.launch {
        repository.deleteAllFav()
    }

    fun deleteAllRecentSearch()=viewModelScope.launch {
        repository.deleteAllRecSearch()
    }

}













