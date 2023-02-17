package com.bersyte.weatherapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.FavouriteAdapterLayoutBinding
import com.bersyte.weatherapp.listner.OnItemSelected
import com.bersyte.weatherapp.db.RecSearchFavWeatherModel


class FavouriteRecentSearchAdapter(
    var weatherList: List<RecSearchFavWeatherModel>,
    var onItemClick: OnItemSelected
) : RecyclerView.Adapter<FavouriteRecentSearchAdapter.AdapterViewHolder>() {

    private var dataModelList: List<RecSearchFavWeatherModel>? = null

    class AdapterViewHolder(val binding: FavouriteAdapterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val binding: FavouriteAdapterLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.favourite_adapter_layout, parent, false
        )
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.binding.model = weatherList[position]
        holder.binding.executePendingBindings()
        holder.binding.itemClickListener = onItemClick
    }
    //02312641099


    override fun getItemCount(): Int {
        return dataModelList!!.size
    }
}