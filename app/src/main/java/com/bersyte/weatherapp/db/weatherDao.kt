package com.bersyte.weatherapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface weatherDao {

    @Query("SELECT * FROM RecSearchFavWeatherModel where isFav=:isFav")
    suspend fun getAllFavourite(isFav:Boolean): List<RecSearchFavWeatherModel>


    @Query("SELECT * FROM RecSearchFavWeatherModel where isRecentSearch=:isRecentSearch")
    suspend fun getAllRecentSearch(isRecentSearch:Boolean): List<RecSearchFavWeatherModel>

    @Insert
    suspend fun addToRecentSearch(recFavWeatherModel: RecSearchFavWeatherModel)

    @Insert
    suspend fun addToFav(recFavWeatherModel: RecSearchFavWeatherModel)


    @Query("UPDATE RecSearchFavWeatherModel SET isFav=:isFav WHERE id = :id")
    fun updateToRecentSearchToFav(isFav: Boolean, id: Int)
     //remove from favourite
     //remove all from favourite
     //add to recent search



    @Delete
    suspend fun delete(recFavWeatherModel: RecSearchFavWeatherModel)

    @Query("Delete from RecSearchFavWeatherModel where isFav= :isFav")
    fun deleteAllFavRecords(isFav: Boolean)

    @Query("Delete from RecSearchFavWeatherModel where isRecentSearch=:isRecentSearch")
    fun deleteAllRecentSearch(isRecentSearch:Boolean)


}