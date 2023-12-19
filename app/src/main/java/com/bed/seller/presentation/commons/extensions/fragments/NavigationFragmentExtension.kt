package com.bed.seller.presentation.commons.extensions.fragments

import android.os.Bundle

import androidx.annotation.IdRes

import androidx.fragment.app.Fragment

import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

fun Fragment.navigateBack() = findNavController().popBackStack()

fun Fragment.navigateBack(@IdRes destination: Int) = findNavController().popBackStack(destination, true)

fun Fragment.navigateTo(@IdRes destination: Int) = findNavController().navigate(destination)

fun Fragment.navigateTo(directions: NavDirections) = findNavController().navigate(directions)

fun Fragment.navigateTo(@IdRes destination: Int, args: Bundle) =
    findNavController().navigate(destination, args)

fun Fragment.navigateTo(directions: NavDirections, args: FragmentNavigator.Extras) =
    findNavController().navigate(directions, args)
