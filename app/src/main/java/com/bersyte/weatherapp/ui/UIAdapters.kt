package com.bersyte.weatherapp.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bersyte.weatherapp.R
import com.google.android.material.button.MaterialButton

@BindingAdapter("setIcon")
fun MaterialButton.setIcon(isFav:Boolean?){
    if(isFav!=null) {
        if (isFav) {
            this.icon = resources.getDrawable(R.mipmap.icon_favourite_active)
        } else {
            this.icon = resources.getDrawable(R.mipmap.icon_favourite)

        }
    }else{
        this.icon = resources.getDrawable(R.mipmap.icon_favourite)
    }
}