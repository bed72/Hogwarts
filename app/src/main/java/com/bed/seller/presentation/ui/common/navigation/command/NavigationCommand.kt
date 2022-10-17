package com.bed.seller.presentation.ui.common.navigation.command

import androidx.annotation.IdRes
import androidx.navigation.NavDirections

sealed class NavigationCommand {
    object Back : NavigationCommand()
    data class Navigation(@IdRes val direction: Int) : NavigationCommand()
    data class NavigationDirection(val directions: NavDirections) : NavigationCommand()
}
