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
   /* private fun startMainActivity() {

        // if (isSplash){

        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1000)

                    (activity as MainActivity?)?.showFragment(HomeFragment(), false)

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        timer.start()
        // }
    }
*/
}