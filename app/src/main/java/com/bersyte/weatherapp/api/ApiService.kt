package com.bersyte.weatherapp.api

import com.bersyte.weatherapp.model.WeatherDataModel
import com.bersyte.weatherapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

      @GET("weather?appid=${Constants.API_KEY}")
      suspend  fun getWeather(@Query("q") cityName: String?): Response<WeatherDataModel>

}