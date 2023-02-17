package com.bersyte.weatherapp.ui

import android.graphics.ColorSpace.Model
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.FavouriteAdapterLayoutBinding
import com.bersyte.weatherapp.db.RecSearchFvWeatherModel
import com.bersyte.weatherapp.listner.OnItemSelected
import com.bersyte.weatherapp.utils.Constants.REMOVE_ONLY_FAV
import java.util.*


class FavouriteRecentSearchAdapter(
    var weatherList: ArrayList<RecSearchFvWeatherModel>,
    var onItemClick: OnItemSelected
) : RecyclerView.Adapter<FavouriteRecentSearchAdapter.AdapterViewHolder>() {

    var arrayList = ArrayList<RecSearchFvWeatherModel>()

    class AdapterViewHolder(val binding: FavouriteAdapterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val binding: FavouriteAdapterLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.favourite_adapter_layout, parent, false
        )
        arrayList.addAll(weatherList)
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.binding.model = weatherList[position]
        holder.binding.executePendingBindings()
        holder.binding.favIconImageButton.setOnClickListener {
            onItemClick.onItemClick(weatherList[position],REMOVE_ONLY_FAV)
        }
    }
    //02312641099


    override fun getItemCount(): Int {
        return if (weatherList!!.isNotEmpty()) {
            weatherList.size
        } else {
            0
        }
    }

    fun filter(charText: String?) {
        var charText = charText
        charText = charText?.lowercase(Locale.getDefault())
        weatherList.clear()
        if (charText!!.isEmpty()) {
            //if (weatherList.size != arrayList.size) {
            weatherList.addAll(arrayList)
            //}
        } else {
            for (model in arrayList) {
                if (model.cityName?.toLowerCase(Locale.getDefault())
                    !!.contains(charText!!)
                ) {
                    weatherList.add(model)
                }
            }
        }
        notifyDataSetChanged()
    }
}