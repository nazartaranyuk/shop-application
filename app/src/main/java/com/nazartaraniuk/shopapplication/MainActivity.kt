package com.nazartaraniuk.shopapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.nazartaraniuk.shopapplication.databinding.ActivityMainBinding
import com.nazartaraniuk.shopapplication.presentation.common.setupWithNavController
import com.nazartaraniuk.shopapplication.presentation.explore_screen.ExploreFragment
import com.nazartaraniuk.shopapplication.presentation.home_screen.HomeFragment


class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupBottomNavigation() {

        val bottomNavigationView = binding.bottomNavigation

        val navGraphIds = listOf(
            R.navigation.home_nav_graph,
            R.navigation.favorites_nav_graph,
            R.navigation.explore_nav_graph,
            R.navigation.account_nav_graph
        )

        bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

    }
}