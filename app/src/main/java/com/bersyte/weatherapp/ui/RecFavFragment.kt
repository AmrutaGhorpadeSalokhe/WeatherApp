package com.bersyte.weatherapp.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.FragmentRecFavBinding
import com.bersyte.weatherapp.db.RecSearchFvWeatherModel
import com.bersyte.weatherapp.listner.OnItemSelected
import com.bersyte.weatherapp.utils.Constants
import com.bersyte.weatherapp.utils.Constants.REMOVE_ONLY_FAV
import com.bersyte.weatherapp.utils.hideKeyboardFrom
import com.bersyte.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecFavFragment : Fragment(R.layout.fragment_rec_fav), OnItemSelected {

    private var _binding: FragmentRecFavBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataModelList: ArrayList<RecSearchFvWeatherModel>
    private val viewModel: WeatherViewModel by viewModels()
    lateinit var myRecyclerViewAdapter: FavouriteRecentSearchAdapter
    private var isFavSearch = true
    private lateinit var filterList: ArrayList<RecSearchFvWeatherModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecFavBinding.inflate(inflater, container, false)
        binding.weatheViewModel=viewModel
        binding.lifecycleOwner = this
        populateData()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataModelList = ArrayList()
        filterList = ArrayList()
        val bundle = this.arguments
        if (bundle != null) {
            isFavSearch = bundle.getBoolean(Constants.OPEN_FROM_FAV)
        }
    }

    private fun populateData() {
        viewModel.setFav(isFavSearch)
        lifecycleScope.launch {
            viewModel.getAllFavoriteCity(isFavSearch)
        }
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        myRecyclerViewAdapter = FavouriteRecentSearchAdapter(dataModelList, this,filterList)
        binding.recyclerView.layoutManager = llm
        binding.recyclerView.adapter = myRecyclerViewAdapter
        viewModel.favResponse.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                setListVisiblity(false)
            } else {
                dataModelList.clear()
                dataModelList.addAll(list)
                filterList.addAll(list)
                myRecyclerViewAdapter.notifyDataSetChanged()
                setListVisiblity(true)
            }
        }

        binding.toolBar.setNavigationOnClickListener {
            hideKeyboardFrom(context!!, binding.root)
            (activity as MainActivity?)?.onBackPressed()
        }
        setupSearchView()
    }

    private fun setupSearchView() {
        binding.searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (!p0.equals("")) {
                    myRecyclerViewAdapter.filter(p0!!)

                } else {
                    myRecyclerViewAdapter.filter("")
                    hideKeyboardFrom(context!!, binding.root)

                }
                return false
            }
        })

        binding.searchButton.setOnCloseListener(object : SearchView.OnCloseListener,
            android.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                hideKeyboardFrom(context!!, binding.root)
                binding.searchButton.onActionViewCollapsed()

                return true
            }
        })
        binding.removeAllText.setOnClickListener {

            if (isFavSearch) {
                showDialog(
                    getString(com.bersyte.weatherapp.R.string.remodeAllFavString),
                    isFavSearch
                )
            } else {
                showDialog(
                    getString(com.bersyte.weatherapp.R.string.remodeAllRecSearchString),
                    isFavSearch
                )
            }
        }
    }

    private fun showDialog(message: String, isFavSearch: Boolean): Boolean {
        var showDialog: Boolean = false
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
        builder.setTitle("Alert !")
        builder.setCancelable(false)
        builder.setPositiveButton(
            getString(R.string.yes)
        ) { dialog: DialogInterface?, _: Int ->
            if (isFavSearch) {
                viewModel.deleteAllFav()
                setListVisiblity(false)
            } else {
                viewModel.deleteAllRecentSearch()
                setListVisiblity(false)
            }
        }
        builder.setNegativeButton(
            getString(R.string.no)
        ) { dialog: DialogInterface, _: Int ->
            showDialog = false
            dialog.cancel()
        }.show()

        return showDialog
    }

    private fun setListVisiblity(visible: Boolean) {
        if (visible) {
            binding.searchButton.visibility = View.VISIBLE
            binding.iconNothingImageview.visibility = View.GONE
            binding.group.visibility = View.VISIBLE
        } else {
            binding.searchButton.visibility = View.GONE
            binding.iconNothingImageview.visibility = View.VISIBLE
            binding.group.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding?.unbind()
    }

    override fun onItemClick(recFavWeatherModel: RecSearchFvWeatherModel, removeFlag: String) {
        when (removeFlag) {
            REMOVE_ONLY_FAV -> {
                val tempModel: RecSearchFvWeatherModel
                if (recFavWeatherModel.isFav!!) {
                    tempModel = RecSearchFvWeatherModel(
                        id = recFavWeatherModel.id,
                        cityName = recFavWeatherModel.cityName,
                        cityTempInDegree = recFavWeatherModel.cityTempInDegree,
                        cityTempInWords = recFavWeatherModel.cityTempInWords,
                        isFav = false,
                        isRecentSearch = recFavWeatherModel.isRecentSearch
                    )
                } else {
                    tempModel = RecSearchFvWeatherModel(
                        id = recFavWeatherModel.id,
                        cityName = recFavWeatherModel.cityName,
                        cityTempInDegree = recFavWeatherModel.cityTempInDegree,
                        cityTempInWords = recFavWeatherModel.cityTempInWords,
                        isFav = true,
                        isRecentSearch = recFavWeatherModel.isRecentSearch
                    )
                }
                viewModel.addRemoveFav(tempModel, isFavSearch)
            }
        }
    }
}