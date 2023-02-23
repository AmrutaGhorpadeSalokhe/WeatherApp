package com.bersyte.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.FragmentSplashBinding
import com.bersyte.weatherapp.utils.Constants.HOME_TAG


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private  var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        startMainActivity()
        return binding.root
    }

    private fun startMainActivity() {
        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                    (activity as MainActivity?)?.showFragment(HomeFragment(), false,HOME_TAG)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        timer.start()
    }
}