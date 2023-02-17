package com.bersyte.weatherapp.repository

import com.bersyte.weatherapp.api.ApiService
import com.bersyte.weatherapp.db.RecSearchFavWeatherModel
import com.bersyte.weatherapp.db.weatherDao
import javax.inject.Inject

class WeatherRepository
@Inject
constructor(private val apiService: ApiService,private val dao: weatherDao) {

    suspend fun getWeather(cityName:String) = apiService.getWeather(cityName = cityName)

    suspend fun getAllRecentSearch() = dao.getAllRecentSearch()

    suspend fun getAllFavCity() = dao.getAllFavourite()

    suspend fun addToFav(recFavWeatherModel: RecSearchFavWeatherModel)=dao.addToFav(recFavWeatherModel)

  //  suspend fun addToFav(recFavWeatherModel: recFavWeatherModel)=dao.addToFav(recFavWeatherModel)

}