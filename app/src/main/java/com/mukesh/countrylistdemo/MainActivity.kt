package com.mukesh.countrylistdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.mukesh.countrylistdemo.databinding.ActivityMainBinding
import com.mukesh.countrylistdemo.ui.countries.list.CountryListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        //set list of top root fragment
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.authFragment,
                R.id.countryListFragment
            )
        )
        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.nav_host_fragment),
            appBarConfiguration
        )

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}