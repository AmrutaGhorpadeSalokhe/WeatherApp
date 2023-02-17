package com.bersyte.weatherapp.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecSearchFvWeatherModel::class], version = 1,
   )
abstract class AppDatabase:RoomDatabase() {

    abstract fun getDao():weatherDao
}