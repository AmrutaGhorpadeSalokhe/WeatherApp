package com.bersyte.weatherapp.ui

import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.ActivityMainBinding
import com.bersyte.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showFragment(SplashFragment(), false)

    }

    fun showFragment(fragment: Fragment, b: Boolean) {
        binding.apply {
            if (b) {
                val fm = supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                fm.add(rootFragment.id, fragment)
                    .commit()

            } else {
                val fm = supportFragmentManager.beginTransaction()
                fm.add(rootFragment.id, fragment).commitAllowingStateLoss()
            }
        }
    }

    fun showCityWeather(fragment: Fragment, bundle: Bundle) {
        val fm = supportFragmentManager.beginTransaction()
        fragment.arguments = bundle
        fm.add(R.id.rootFragment, fragment)
            .addToBackStack(null)
            .commit()


    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do you want to exit ?")
            builder.setTitle("Alert !")
            builder.setCancelable(false)
            builder.setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                    finish()
                })
            builder.setNegativeButton("No",
                DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                    dialog.cancel()
                }).show()
        }
    }
}