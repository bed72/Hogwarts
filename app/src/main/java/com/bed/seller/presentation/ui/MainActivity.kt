package com.bed.seller.presentation.ui

import com.bed.seller.R

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.bed.seller.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarApp)

        setupNavigationBarController()
        setupAppComponentBarController()
        setupComponentNavigationController()
    }

    private fun setupNavigationBarController() {
        val navigationContainerFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment

        navController = navigationContainerFragment.navController
        binding.bottomNavMain.setupWithNavController(navController)
    }

    private fun setupAppComponentBarController() {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.sign_in_fragment,
                R.id.sign_up_fragment,
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.toolbarApp.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupComponentNavigationController() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            visibilityNavBar(destination.id)
            visibilityToolBar(destination.id)
            visibilityGoBackInToolBar(destination.id)
        }
    }

    private fun visibilityNavBar(destination: Int) {
        val visibility = when (destination) {
            R.id.auth -> GONE
            else -> GONE
        }

        binding.bottomNavMain.visibility = visibility
    }

    private fun visibilityToolBar(destination: Int) {
        val visibility = when (destination) {
            R.id.auth -> GONE
            else -> GONE
        }

        binding.toolbarApp.visibility = visibility
    }

    private fun visibilityGoBackInToolBar(destination: Int) {
        val isTopLevelDestination = appBarConfiguration.topLevelDestinations.contains(destination)

        if (!isTopLevelDestination) binding.toolbarApp.setNavigationIcon(R.drawable.ic_back)
    }

    companion object {
        private const val GONE = View.GONE
        private const val VISIBLE = View.VISIBLE
    }
}
