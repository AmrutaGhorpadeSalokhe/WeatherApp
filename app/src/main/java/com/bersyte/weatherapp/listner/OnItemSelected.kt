package com.bersyte.weatherapp.listner

import com.bersyte.weatherapp.db.RecSearchFavWeatherModel

interface OnItemSelected {
    fun onItemClick(recFavWeatherModel: RecSearchFavWeatherModel)
}