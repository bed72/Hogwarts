package com.bed.seller.presentation.commons.constants

import androidx.annotation.NavigationRes

import com.bed.seller.R

import com.bed.seller.presentation.commons.states.States

object ScreensConstants {
    // Remove icon go back
    val showAppBarIn = setOf(
        R.id.home_fragment,
        R.id.offer_fragment,
        R.id.products_fragment,
        R.id.dashboard_exit_fragment,
    )
    //Remove toolbar
    fun showToolBarIn(@NavigationRes destination: Int) = when (destination) {
        R.id.home_fragment, R.id.products_fragment -> States.VISIBLE
        R.id.setting_fragment, R.id.notification_fragment -> States.VISIBLE
        R.id.offer_fragment, R.id.dashboard_exit_fragment, R.id.sign_out_fragment -> States.VISIBLE
        else -> States.GONE
    }
    fun showBottomBarIn(@NavigationRes destination: Int) = when (destination) {
        R.id.home_fragment, R.id.products_fragment -> States.VISIBLE
        R.id.offer_fragment, R.id.dashboard_exit_fragment -> States.VISIBLE
        else -> States.GONE
    }
}
