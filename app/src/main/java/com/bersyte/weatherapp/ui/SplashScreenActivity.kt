package com.bersyte.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bersyte.weatherapp.databinding.ActivityMainBinding
import com.bersyte.weatherapp.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

   private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}