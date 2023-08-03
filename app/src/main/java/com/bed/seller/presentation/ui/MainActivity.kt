package com.bed.seller.presentation.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator

import androidx.annotation.NavigationRes

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController

import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.MainActivityBinding

import com.bed.seller.presentation.commons.extensions.dialog
import com.bed.seller.presentation.commons.connection.CheckConnection
import com.bed.seller.presentation.commons.connection.CheckConnectionImpl
import com.bed.seller.presentation.commons.extensions.preventScreenshotsAndRecentAppThumbnails

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val alert: AlertDialog by lazy {
        dialog(this, R.layout.dialog_connection_fragment)
    }

    private lateinit var connection: CheckConnection
    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        setSplashScreen()

        super.onCreate(savedInstanceState)

        preventScreenshotsAndRecentAppThumbnails()

        connection = CheckConnectionImpl(application)
        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupConnection()
        setupNavigationBarController()
        setupComponentAppBarController()
        setupComponentNavigationBarController()
    }

    private fun setSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition { false }
            setOnExitAnimationListener { setAnimation(it) }
        }
    }

    private fun setAnimation(provider: SplashScreenViewProvider) {
        provider.iconView
            .animate()
            .rotation(ANIMATION_ROTATION)
            .setDuration(ANIMATION_DURATION)
            .translationZ(ANIMATION_PROPERTY)
            .setInterpolator(AnticipateInterpolator())
            .withEndAction { provider.remove() }
            .start()
    }

    private fun setupConnection() {
        connection.observe(this) { if (it) alert.dismiss() else alert.show() }
    }

    private fun setupNavigationBarController() {
        val navigationContainerFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment

        navController = navigationContainerFragment.navController

        binding.bottomNavMain.setupWithNavController(navController)
    }

    private fun setupComponentAppBarController() {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_fragment,
                R.id.sale_fragment,
                R.id.products_fragment,
                R.id.exit_fragment,
                R.id.exit_fragment,
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupComponentNavigationBarController() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            visibilityToolBar(destination.id)
            visibilityBottomBar(destination.id)
            visibilityGoBackInToolBar(destination.id)
        }
    }

    private fun visibilityBottomBar(@NavigationRes destination: Int) {
        binding.bottomNavMain.visibility = when (destination) {
            R.id.home_fragment, R.id.products_fragment -> VISIBLE
            R.id.exit_fragment, R.id.sale_fragment -> VISIBLE
            else -> GONE
        }
    }

    private fun visibilityToolBar(@NavigationRes destination: Int) {
        with (binding.toolbar) {
            visibility = when (destination) {
                R.id.home_fragment, R.id.products_fragment -> VISIBLE
                R.id.exit_fragment, R.id.sale_fragment -> VISIBLE
                R.id.setting_fragment, R.id.notification_fragment -> VISIBLE
                else -> GONE
            }
        }
    }

    private fun visibilityGoBackInToolBar(@NavigationRes destination: Int) {
        val isTopLevelDestination = appBarConfiguration.topLevelDestinations.contains(destination)

        if (isTopLevelDestination.not()) binding.toolbar.setNavigationIcon(R.drawable.ic_back)
    }

    companion object {
        private const val GONE = View.GONE
        private const val VISIBLE = View.VISIBLE

        private const val ANIMATION_PROPERTY = 0F
        private const val ANIMATION_ROTATION = 360F
        private const val ANIMATION_DURATION = 2000L
    }
}
