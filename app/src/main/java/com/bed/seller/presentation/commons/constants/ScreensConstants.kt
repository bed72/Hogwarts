package com.bed.seller.presentation.commons.constants

import androidx.annotation.NavigationRes

import com.bed.seller.R

import com.bed.seller.presentation.commons.states.States

object ScreensConstants {
    val showAppBarIn = setOf(
        R.id.home_fragment,
        R.id.offer_fragment,
        R.id.products_fragment,
        R.id.dashboard_exit_fragment,
    )
    fun showBottomBarIn(@NavigationRes destination: Int) = when (destination) {
        R.id.home_fragment, R.id.products_fragment -> States.VISIBLE
        R.id.offer_fragment, R.id.dashboard_exit_fragment -> States.VISIBLE
        else -> States.GONE
    }
    fun showToolbarIn(@NavigationRes destination: Int) = when (destination) {
        R.id.home_fragment, R.id.products_fragment -> States.VISIBLE
        R.id.setting_fragment, R.id.notification_fragment -> States.VISIBLE
        R.id.offer_fragment, R.id.dashboard_exit_fragment -> States.VISIBLE
        else -> States.GONE
    }
}
