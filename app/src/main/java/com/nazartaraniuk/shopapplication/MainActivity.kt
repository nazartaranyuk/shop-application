package com.nazartaraniuk.shopapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nazartaraniuk.shopapplication.databinding.ActivityMainBinding
import com.nazartaraniuk.shopapplication.presentation.explore_screen.ExploreFragment
import com.nazartaraniuk.shopapplication.presentation.home_screen.HomeFragment


class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setUpBottomView()
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setUpBottomView() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.explore_menu -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.navHostFragment, ExploreFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.shop_menu -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.navHostFragment, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
            true
        }
        binding.bottomNavigation.setOnItemReselectedListener {
            when(it.itemId) {
                R.id.explore_menu -> {}
            }
        }
    }
}