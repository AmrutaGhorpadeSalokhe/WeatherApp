package com.bersyte.weatherapp.ui

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.FragmentSearchBinding
import com.bersyte.weatherapp.utils.Constants
import com.bersyte.weatherapp.utils.hideKeyboardFrom
import okhttp3.internal.notify


class SearchFragment : Fragment(R.layout.fragment_search) {

    private  var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArrayAdapter<String>
    val cityList = Constants.cityList
   // var cityDumyList=ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        //status_bar color
        setupListView()
        setupSearchView()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun setupListView() {
        adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, cityList)
        binding.listView.adapter = adapter
        binding.listView.visibility=View.INVISIBLE

        binding.backButton.setOnClickListener {
            hideKeyboardFrom(context!!,binding.root)
            (activity as MainActivity?)?.showFragment(HomeFragment(), false)

        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.listView.visibility=View.INVISIBLE
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(!p0.equals("")) {
                    adapter.filter.filter(p0)
                    binding.listView.visibility = View.VISIBLE
                }else{
                    hideKeyboardFrom(context!!,binding.root)
                    binding.listView.visibility = View.GONE
                }
                return false
            }
        })

        binding.searchView.setOnCloseListener(object : SearchView.OnCloseListener,
            android.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                binding.listView.visibility=View.INVISIBLE
                return true
            }
        })

        binding.listView.setOnItemClickListener { adapterView, view, i, l ->
            hideKeyboardFrom(context!!,binding.root)
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString("city", cityList[i].toString())

            fragment.arguments = bundle
            (activity as MainActivity?)?.showCityWeather(fragment,bundle)

            //(activity as MainActivity?)?.showFragment(HomeFragment(), false)
        }
    }







}