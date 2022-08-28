package com.nazartaraniuk.shopapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nazartaraniuk.shopapplication.databinding.ActivityMainBinding
import com.nazartaraniuk.shopapplication.presentation.NotificationWorker
import com.nazartaraniuk.shopapplication.presentation.SignInActivity
import com.nazartaraniuk.shopapplication.presentation.account_screen.AccountFragment
import com.nazartaraniuk.shopapplication.presentation.common.SharedPreferencesHelper
import com.nazartaraniuk.shopapplication.presentation.common.loadImage
import com.nazartaraniuk.shopapplication.presentation.common.setupWithNavController
import com.nazartaraniuk.shopapplication.presentation.di.getComponent
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var auth: FirebaseAuth
    private var binding: ActivityMainBinding? = null
    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.application.getComponent().inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        startShowingNotifications()
        val isTablet = this.resources.getBoolean(R.bool.isTablet)
        if (isTablet) {
            subscribeToLiveData()
            setupMasterDetailNavigation()
        }
        setupBottomNavigation()
        setContentView(binding?.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun startShowingNotifications() {
        if (sharedPreferencesHelper.getFromPreference()) {
            startSendingNotifications()
        }
    }

    private fun setupBottomNavigation() {

        val bottomNavigationView = binding?.bottomNavigation

        val navGraphIds = listOf(
            R.navigation.home_nav_graph,
            R.navigation.favorites_nav_graph,
            R.navigation.explore_nav_graph,
            R.navigation.account_nav_graph
        )

        bottomNavigationView?.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

    }

    private fun setupMasterDetailNavigation() {
        val user = auth.currentUser
        loadImage(
            binding?.ivAccountAvatar ?: error("Can't load photo"),
            user?.photoUrl.toString()
        )
        binding?.tvAccountEmail?.text = user?.email
        binding?.tvAccountFirstName?.text = user?.displayName
        binding?.tvLogOut?.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.item_detail_nav_container) as NavHostFragment
        binding?.tvGoToExplore?.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.action_global_exploreFragment)
        }
        binding?.tvGoToFavorites?.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.action_global_favoritesFragment)
        }
        binding?.tvGoToHome?.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.action_global_homeFragment)
        }
        binding?.switchSendNotifications?.setOnClickListener {
            val isChecked = (it as Switch).isChecked
            if (isChecked) {
                viewModel.saveSwitchState(isChecked)
                startSendingNotifications()
                Toast.makeText(this, AccountFragment.TOAST_TEXT, Toast.LENGTH_LONG).show()
                Log.d(AccountFragment.TAG, AccountFragment.LOG_MESSAGE)
            } else {
                viewModel.saveSwitchState(isChecked)
                stopSendingNotifications()
            }
        }
    }

    private fun subscribeToLiveData() = with(viewModel) {
        isChecked.observe(this@MainActivity) {
            binding?.switchSendNotifications?.isChecked = it
        }
    }

    fun stopSendingNotifications() {
        WorkManager.getInstance(this).cancelUniqueWork(UNIQUE_WORK_NAME)
    }

    fun startSendingNotifications() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()
        val workRequest = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            15, // TODO move this to companion object
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .addTag(UNIQUE_WORK_NAME)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    companion object {
        const val UNIQUE_WORK_NAME = "Notification worker"
    }
}