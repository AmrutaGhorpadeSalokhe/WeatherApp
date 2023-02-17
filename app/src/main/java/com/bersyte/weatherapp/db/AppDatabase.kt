package com.bersyte.weatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecSearchFavWeatherModel::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getDao():weatherDao
}