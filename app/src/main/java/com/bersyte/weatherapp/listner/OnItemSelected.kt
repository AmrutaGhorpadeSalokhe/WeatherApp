package com.bersyte.weatherapp.listner

import com.bersyte.weatherapp.db.RecSearchFvWeatherModel

interface OnItemSelected {
    fun onItemClick(recFavWeatherModel: RecSearchFvWeatherModel,removeFlag:String)
}