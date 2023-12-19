package com.bed.seller.presentation.commons.constants

import androidx.annotation.NavigationRes

import com.bed.seller.R

import com.bed.seller.presentation.commons.states.ConstantStates

object ScreensConstants {
    // Remove icon go back
    val removeGoBackIcon = setOf(
        R.id.home_fragment,
        R.id.products_fragment,
        R.id.dashboard_exit_fragment,
    )

    //Remove toolbar
    fun showToolBarIn(@NavigationRes destination: Int) = when (destination) {
        R.id.offer_fragment, R.id.gallery_fragment -> ConstantStates.VISIBLE
        R.id.home_fragment, R.id.products_fragment -> ConstantStates.VISIBLE
        R.id.setting_fragment, R.id.notification_fragment -> ConstantStates.VISIBLE
        R.id.dashboard_exit_fragment, R.id.sign_out_fragment -> ConstantStates.VISIBLE
        else -> ConstantStates.GONE
    }
    fun showBottomBarIn(@NavigationRes destination: Int) = when (destination) {
        R.id.dashboard_exit_fragment -> ConstantStates.VISIBLE
        R.id.home_fragment, R.id.products_fragment -> ConstantStates.VISIBLE
        else -> ConstantStates.GONE
    }
}
