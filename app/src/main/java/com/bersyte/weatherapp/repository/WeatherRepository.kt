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

    suspend fun addToRecentSearch(recFavWeatherModel: RecSearchFvWeatherModel) =
        dao.addToRecentSearch(recFavWeatherModel)

    suspend fun updateFavFlag(isFav: Boolean, id: Int) = dao.updateFavFlag(isFav, id)

    suspend fun deleteAllFav() = dao.deleteAllFavRecords(true)

    suspend fun deleteAllRecSearch() = dao.deleteAllRecentSearch(true)

    suspend fun getFavModel(id: Int) = dao.getFavouriteModel(id = id)

    suspend fun checkItemPresent(cityName: String) = dao.checkItemPresent(cityName)
    suspend fun update(recFavWeatherModel: RecSearchFvWeatherModel) = dao.update(recFavWeatherModel)
}