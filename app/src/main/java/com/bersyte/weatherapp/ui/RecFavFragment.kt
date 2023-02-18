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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.FragmentRecFavBinding
import com.bersyte.weatherapp.db.RecSearchFvWeatherModel
import com.bersyte.weatherapp.listner.OnItemSelected
import com.bersyte.weatherapp.utils.Constants.REMOVE_ONLY_FAV
import com.bersyte.weatherapp.utils.hideKeyboardFrom
import com.bersyte.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecFavFragment : Fragment(R.layout.fragment_rec_fav), OnItemSelected {

    private var _binding: FragmentRecFavBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataModelList: ArrayList<RecSearchFvWeatherModel>
    private val viewModel: WeatherViewModel by viewModels()
    lateinit var myRecyclerViewAdapter: FavouriteRecentSearchAdapter
    private var isFavSearch = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecFavBinding.inflate(inflater, container, false)
        populateData()
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataModelList = ArrayList()
        val bundle = this.arguments
        if (bundle != null) {
            isFavSearch = bundle.getBoolean("isFavSearch")
        }
    }

    private fun populateData() {
        viewModel.getAllFavoriteCity()
        viewModel.favResponse.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                setListVisiblity(false)
            } else {
                val llm: LinearLayoutManager = LinearLayoutManager(context)
                llm.orientation = LinearLayoutManager.VERTICAL
                myRecyclerViewAdapter = FavouriteRecentSearchAdapter(dataModelList, this)
                binding.recyclerView.layoutManager = llm
                binding.recyclerView.adapter = myRecyclerViewAdapter
                dataModelList.addAll(list)
                binding.recyclerView.adapter?.notifyDataSetChanged()
                setListVisiblity(true)
            }

        }
        binding.toolBar.setNavigationOnClickListener {
            hideKeyboardFrom(context!!,binding.root)
            (activity as MainActivity?)?.showFragment(HomeFragment(), false)
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
                if (showDialog(getString(com.bersyte.weatherapp.R.string.remodeAllFavString))) {
                    viewModel.deleteAllFav()
                }
            } else {
                if (showDialog(getString(com.bersyte.weatherapp.R.string.remodeAllRecSearchString))) {
                    viewModel.deleteAllRecentSearch()
                }
            }
            setListVisiblity(false)

        }


        /* binding.listView.setOnItemClickListener { adapterView, view, i, l ->
             hideKeyboardFrom(context!!,binding.root)
             val fragment = HomeFragment()
             val bundle = Bundle()
             bundle.putString("city", cityList[i].toString())

             fragment.arguments = bundle
             (activity as MainActivity?)?.showCityWeather(fragment,bundle)

             //(activity as MainActivity?)?.showFragment(HomeFragment(), false)
         }*/
    }

    private fun showDialog(message: String): Boolean {
        /* val builder = AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
        builder.setTitle("Dialog")

        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        builder.setNegativeButton("Cancel", null)
        builder.show()*/
        var showDialog: Boolean = false
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setTitle("Alert !")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "Yes"
        ) { dialog: DialogInterface?, _: Int ->
            showDialog = true
        }
        builder.setNegativeButton(
            "No"
        ) { dialog: DialogInterface, _: Int ->
            showDialog = false
            dialog.cancel()
        }
        return showDialog
    }

    private fun setListVisiblity(visible: Boolean) {
        if (visible) {
            binding.iconNothingImageview.visibility = View.GONE
            binding.group.visibility = View.VISIBLE
        } else {
            binding.iconNothingImageview.visibility = View.VISIBLE
            binding.group.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onItemClick(recFavWeatherModel: RecSearchFvWeatherModel, removeFlag: String) {
        when (removeFlag) {
            REMOVE_ONLY_FAV -> {
                    viewModel.addRemoveFav(recFavWeatherModel)
            }
        }

    }


}