package com.bersyte.weatherapp.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class RecSearchFvWeatherModel(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name ="id") val id:Int,
    @ColumnInfo(name = "cityName") val cityName: String?,
    @ColumnInfo(name = "cityTempInDegree") val cityTempInDegree: String?,
    @ColumnInfo(name = "cityTempInWords") val cityTempInWords: String?,
    @ColumnInfo(name = "isFav") var isFav: Boolean?,
    @ColumnInfo(name = "isRecentSearch") var isRecentSearch: Boolean?,

) : Parcelable
