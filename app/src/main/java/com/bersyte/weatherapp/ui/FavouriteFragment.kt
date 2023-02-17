package com.bersyte.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.FragmentFavouriteBinding
import com.bersyte.weatherapp.db.RecSearchFvWeatherModel
import com.bersyte.weatherapp.listner.OnItemSelected
import com.bersyte.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_favourite),OnItemSelected {

    private  var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataModelList: ArrayList<RecSearchFvWeatherModel>
    private val viewModel: WeatherViewModel by viewModels()
    lateinit var myRecyclerViewAdapter:FavouriteRecentSearchAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

       // _binding = FragmentFavouriteBinding.inflate(inflater, container, false)

        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_favourite, container, false)
        binding.adapter=myRecyclerViewAdapter
        // populateData()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataModelList=ArrayList()
        val llm: LinearLayoutManager = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        myRecyclerViewAdapter=FavouriteRecentSearchAdapter(dataModelList, this)
        binding.recyclerView.layoutManager = llm
        binding.recyclerView.adapter=myRecyclerViewAdapter

    }
   /* private fun populateData() {
        viewModel.getAllFavoriteCity()
        viewModel.favResponse.observe(viewLifecycleOwner) {list->
            dataModelList.addAll(list)
            binding.adapter=myRecyclerViewAdapter

        }

    }*/



    override fun onItemClick(recFavWeatherModel: RecSearchFvWeatherModel, removeFlag: String) {
        TODO("Not yet implemented")
    }


}