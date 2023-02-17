package com.bersyte.weatherapp.db

import androidx.room.*

@Dao
interface weatherDao {

    @Query("SELECT * FROM RecSearchFvWeatherModel where isFav=:isFav")
    suspend fun getAllFavourite(isFav:Boolean): List<RecSearchFvWeatherModel>


    @Query("SELECT * FROM RecSearchFvWeatherModel where isRecentSearch=:isRecentSearch")
    suspend fun getAllRecentSearch(isRecentSearch:Boolean): List<RecSearchFvWeatherModel>

    @Insert
    suspend fun addToRecentSearch(recFavWeatherModel: RecSearchFvWeatherModel)

    @Insert
    suspend fun addToFav(recFavWeatherModel: RecSearchFvWeatherModel)


    @Query("UPDATE RecSearchFvWeatherModel SET isFav=:isFav WHERE id = :id")
    fun updateToRecentSearchToFav(isFav: Boolean, id: Int)
     //remove from favourite
     //remove all from favourite
     //add to recent search



    @Delete
    suspend fun delete(recFavWeatherModel: RecSearchFvWeatherModel)

    @Query("Delete from RecSearchFvWeatherModel where isFav= :isFav")
    fun deleteAllFavRecords(isFav: Boolean)

    @Query("Delete from RecSearchFvWeatherModel where isRecentSearch=:isRecentSearch")
    fun deleteAllRecentSearch(isRecentSearch:Boolean)


}