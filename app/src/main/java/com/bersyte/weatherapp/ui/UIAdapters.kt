package com.bersyte.weatherapp.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.utils.Constants.IMG_URL
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import java.util.*

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

    this.text = android.text.format.DateFormat.format("EEE, d MMM yyyy HH:mm:ss", cal).toString()
}

@BindingAdapter("min","max")
fun TextView.setMinMax(min:String,max:String){
    this.text="$min\u00B0-$max\u00B0"
   /* val tz : TimeZone=timezone"\u00B0
    System.out.println(
        "TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT)
            .toString() + " Timezone id :: " + tz.id
    )*/
    //val tz:TimeZone = TimeZone.getTimeZone(timezone)
   // val cal = Calendar.getInstance(Locale.ENGLISH)
   // this.text= tz.id
    //this.text=cal.timeZone.id


}

@BindingAdapter("setPercentage")
fun TextView.setPercentage(text:String){
    this.text="$text %"
}

@BindingAdapter("setFerCel")
fun TextView.setFerCel(text:String){
    this.text="$text \u2103 \u2109"

}
@BindingAdapter("sunrise","sunset")
fun ImageView.setIcon(sunrise: Long,sunset:Long) {
    val currentTime=Date().time
    return if (currentTime in sunrise until sunset) {
        this.setImageDrawable(resources.getDrawable(R.mipmap.icon_mostly_sunny_small))
    } else {
        this.setImageDrawable(resources.getDrawable(R.mipmap.icon_clear_night))
    }
}
/*

http://openweathermap.org/img/w/10d.png
                app:setIcon="@{weatheViewModel.recFavWeatherModel.isFav()}"
*/
/*

val items = TimeZone.getAvailableIDs().map { id ->
    // val timeZone = TimeZone.getTimeZone(id)
    // val name = timeZone.displayName
    val zone = ZoneId.of(id);
    val offsetToday = OffsetDateTime.now(zone).offset
    val offset = offsetFormatter.format(offsetToday)    // GMT
    val tokens = id.replace("_", " ").split("/")
    val name = if (tokens.size == 2) {
        val (country, city) = tokens
        "$city ($country)"
    }
    else id
    TimezoneView(id, name = name, offsetName = offset, offset = offsetToday.totalSeconds)
}
class TimezoneView(val id: String, val name: String, val offsetName: String, offset: Int)
*/

