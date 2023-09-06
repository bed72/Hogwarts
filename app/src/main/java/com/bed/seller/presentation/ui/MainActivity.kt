package com.bed.seller.presentation.ui

import dagger.hilt.android.AndroidEntryPoint

import android.os.Bundle
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

import com.bed.seller.R

import com.bed.seller.databinding.MainActivityBinding

import com.bed.seller.presentation.commons.extensions.dialog
import com.bed.seller.presentation.commons.constants.ScreensConstants
import com.bed.seller.presentation.commons.connection.CheckConnection
import com.bed.seller.presentation.commons.connection.CheckConnectionImpl
import com.bed.seller.presentation.commons.extensions.preventScreenshotsAndRecentAppThumbnails

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val connection: CheckConnection by lazy { CheckConnectionImpl(application) }
    private val alert: AlertDialog by lazy { dialog(R.layout.dialog_connection_fragment) }

    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().run {
            setKeepOnScreenCondition { false }
            setOnExitAnimationListener { setAnimation(it) }
        }

        preventScreenshotsAndRecentAppThumbnails()

        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupConnection()
        setupNavigationBarController()
        setupComponentAppBarController()
        setupComponentNavigationBarController()
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
        if (connection.isActiveNetworkMetered) alert.show()

        connection.observe(this) { if (it) alert.dismiss() else alert.show() }
    }

    private fun setupNavigationBarController() {
        val navigation = supportFragmentManager.findFragmentById(R.id.navigation) as NavHostFragment

        navController = navigation.navController

        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setupComponentAppBarController() {
        appBarConfiguration = AppBarConfiguration(ScreensConstants.showAppBarIn)

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
        binding.bottomNavigation.visibility = ScreensConstants.showBottomBarIn(destination)
    }

    private fun visibilityToolBar(@NavigationRes destination: Int) {
        binding.toolbar.visibility = ScreensConstants.showToolbarIn(destination)
    }

    private fun visibilityGoBackInToolBar(@NavigationRes destination: Int) {
        val isTopLevelDestination = appBarConfiguration.topLevelDestinations.contains(destination)

        if (isTopLevelDestination.not()) binding.toolbar.setNavigationIcon(R.drawable.ic_back)
    }

    companion object {
        private const val ANIMATION_PROPERTY = 0F
        private const val ANIMATION_ROTATION = 360F
        private const val ANIMATION_DURATION = 2000L
    }
}
