package com.bersyte.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.FragmentSplashBinding


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private  var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        //status_bar color
        activity?.window?.statusBarColor= getResources().getColor(R.color.black)

        startMainActivity()


        return binding.root
    }

    private fun startMainActivity() {

        // if (isSplash){

        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)

                    (activity as MainActivity?)?.showFragment(HomeFragment(), false)

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        timer.start()
        // }
    }




}