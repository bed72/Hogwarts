package com.bed.seller.presentation.ui.common.viewmodel

import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.lifecycle.MutableLiveData

import com.bed.seller.presentation.ui.common.viewmodel.event.Event
import com.bed.seller.presentation.ui.common.navigation.command.NavigationCommand

abstract class BaseViewModel : ViewModel() {
    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

    fun navigate(@IdRes direction: Int) {
        _navigation.value = Event(NavigationCommand.Navigation(direction))
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = Event(NavigationCommand.NavigationDirection(navDirections))
    }
}
