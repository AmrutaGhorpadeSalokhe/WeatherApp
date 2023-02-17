package com.bersyte.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.listner.OnItemSelected
import com.bersyte.weatherapp.databinding.FragmentFavouriteBinding
import com.bersyte.weatherapp.db.RecSearchFavWeatherModel
import com.bersyte.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment :Fragment(R.layout.fragment_favourite),OnItemSelected {

    private  var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    lateinit var dataModelList: ArrayList<RecSearchFavWeatherModel>
    private val viewModel: WeatherViewModel by viewModels()
    private  val myRecyclerViewAdapter:FavouriteRecentSearchAdapter by lazy {
        FavouriteRecentSearchAdapter(dataModelList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)

        //status_bar color
        activity?.window?.statusBarColor= getResources().getColor(R.color.black)

        populateData()
        return binding.root
    }
    private fun populateData() {
        viewModel.getAllFavoriteCity()
        viewModel.favResponse.observe(viewLifecycleOwner) {list->
            dataModelList.addAll(list)
            binding.adapter=myRecyclerViewAdapter

        }

    }

    override fun onItemClick(recFavWeatherModel: RecSearchFavWeatherModel) {

    }


}