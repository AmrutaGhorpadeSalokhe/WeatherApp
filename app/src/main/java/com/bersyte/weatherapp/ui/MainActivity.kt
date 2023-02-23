package com.bersyte.weatherapp.ui

import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bersyte.weatherapp.R
import com.bersyte.weatherapp.databinding.ActivityMainBinding
import com.bersyte.weatherapp.utils.Constants
import com.bersyte.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showFragment(SplashFragment(), false)
    }

    fun showFragment(fragment: Fragment, b: Boolean, tag: String = "") {
        binding.apply {
            if (b) {
                val fm = supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                fm.add(rootFragment.id, fragment, tag)
                    .commit()
            } else {
                val fm = supportFragmentManager.beginTransaction()
                fm.add(rootFragment.id, fragment, tag).commitAllowingStateLoss()
            }
        }
    }

    fun showCityWeather(fragment: Fragment, bundle: Bundle, tag: String = "") {
        val fm = supportFragmentManager.beginTransaction()
        fragment.arguments = bundle
        if (tag == Constants.SEARCH_TAG) {
            fm.replace(R.id.rootFragment, fragment)
                .commitAllowingStateLoss()
            supportFragmentManager.popBackStack()
        }else{
            fm.add(R.id.rootFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(getString(R.string.exit_message))
            builder.setTitle(getString(R.string.alert))
            builder.setCancelable(false)
            builder.setPositiveButton(
                R.string.yes
            ) { dialog: DialogInterface?, which: Int ->
                finish()
            }
            builder.setNegativeButton(
                R.string.no
            ) { dialog: DialogInterface, which: Int ->
                dialog.cancel()
            }.show()
        }
    }
}