package com.bersyte.weatherapp.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bersyte.weatherapp.R
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@BindingAdapter("setIcon")
fun MaterialButton.setIcon(isFav:Boolean?){
    if (isFav != null) {
        if (isFav) {
            this.icon = resources.getDrawable(R.mipmap.icon_favourite_active)
        } else {
            this.icon = resources.getDrawable(R.mipmap.icon_favourite)
        }
    } else {
        this.icon = resources.getDrawable(R.mipmap.icon_favourite)
    }
}

@BindingAdapter("setDate")
fun TextView.setDate(time: Long) {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = time * 1000
    this.text = android.text.format.DateFormat.format("EEE, d MMM yyyy   HH:mm aa", cal).toString()
}

@BindingAdapter("min","max")
fun TextView.setMinMax(min:String,max:String){
    val minDegree=min.toFloat().minus(273.15).roundToInt()
    val maxDegree=max.toFloat().minus(273.15).roundToInt()
    this.text="$minDegree\u00B0-$maxDegree\u00B0"
}

@BindingAdapter("setPercentage")
fun TextView.setPercentage(text: String) {
    this.text = "$text %"
}

@BindingAdapter("setFerCel")
fun TextView.setFerCel(text: String) {
    val toDegree=text.toFloat().minus( 273.15).roundToInt()
   /* ℃ ℉*/
    this.text = "$toDegree"

}

@BindingAdapter("isFromFav", "size")
fun TextView.setCitySizeText(isFromFav: Boolean, size: Int) {
    if (isFromFav) {
        this.text = "$size City added as favourite"
    } else {
        this.text = "You recently searched for"
    }
}

@BindingAdapter("setWeatherImage")
fun ImageView.setWeatherImage(description: String) {
    if (description.isNotEmpty()) {
        when (description) {
            "clear sky" -> {
                this.setImageDrawable(resources.getDrawable(R.mipmap.icon_mostly_sunny_small))
                return
            }
            "few clouds" -> {
                this.setImageDrawable(resources.getDrawable(R.mipmap.icon_partly_cloudy_small))
                return
            }
            "scattered clouds" -> {
                this.setImageDrawable(resources.getDrawable(R.mipmap.icon_mostly_cloudy_small))
                return
            }
            "shower rain" -> {
                this.setImageDrawable(resources.getDrawable(R.mipmap.icon_rain_small))
                return
            }
            "rain" -> {
                this.setImageDrawable(resources.getDrawable(R.mipmap.icon_rain_small))
                return
            }
            "thunderstorm" -> {
                this.setImageDrawable(resources.getDrawable(R.mipmap.icon_thunderstorm_small))
                return
            }
            "snow" -> {
                this.setImageDrawable(resources.getDrawable(R.mipmap.icon_wind_info))
                return
            }
            "mist" -> {
                this.setImageDrawable(resources.getDrawable(R.mipmap.icon_wind_info))
                return
            }
            else -> {
                this.setImageDrawable(resources.getDrawable(R.mipmap.icon_mostly_sunny_small))
                return
            }

        }
    }
}
