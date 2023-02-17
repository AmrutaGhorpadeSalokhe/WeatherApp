package com.bersyte.weatherapp.repository

import com.bersyte.weatherapp.api.ApiService
import com.bersyte.weatherapp.db.RecSearchFvWeatherModel
import com.bersyte.weatherapp.db.weatherDao
import javax.inject.Inject

class WeatherRepository
@Inject
constructor(private val apiService: ApiService, private val dao: weatherDao) {

    suspend fun getWeather(cityName: String) = apiService.getWeather(cityName = cityName)

    suspend fun getAllRecentSearch(isRecentSearch: Boolean) = dao.getAllRecentSearch(isRecentSearch)

    suspend fun getAllFavCity(isFav: Boolean) = dao.getAllFavourite(isFav)

    suspend fun addToFav(recFavWeatherModel: RecSearchFvWeatherModel) =
        dao.addToFav(recFavWeatherModel)

    suspend fun deleteFav(recFavWeatherModel: RecSearchFvWeatherModel) =
        dao.delete(recFavWeatherModel)

    suspend fun deleteAllFav() = dao.deleteAllFavRecords(true)

    suspend fun deleteAllRecSearch() = dao.deleteAllRecentSearch(true)

}