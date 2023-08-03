package com.bed.seller.presentation.commons.extensions

import android.os.Build
import android.os.Bundle

import android.widget.TextView

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.animation.ObjectAnimator

import android.content.Context
import android.content.pm.PackageManager

import android.view.View
import android.view.inputmethod.InputMethodManager

import com.google.android.material.snackbar.Snackbar

import androidx.annotation.IdRes
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat

import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

// Colors
fun Fragment.getColor(@ColorRes id: Int): Int =
     requireActivity().resources.getColor(id, null)

fun Fragment.setStatusBarColor(@ColorRes id: Int) {
    requireActivity().window.statusBarColor = requireActivity().getColor(id)
}

fun Fragment.setNavigationBarColor(@ColorRes id: Int) {
    requireActivity().window.navigationBarColor = requireActivity().getColor(id)
}

// SnackBar
fun Fragment.snake(view: View, message: String) =
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

fun Fragment.snake(view: View, @StringRes message: Int, time: Int = 6000) =
    Snackbar.make(view, getText(message), time).setDuration(time).show()

fun Fragment.snakeArg(view: View, @StringRes message: Int, arg: String) =
    Snackbar.make(view, resources.getString(message, arg), Snackbar.LENGTH_LONG).show()

fun Fragment.snack(
    view: View,
    message: String,
    time: Int = Snackbar.LENGTH_INDEFINITE,
    @StringRes titleAction: Int = android.R.string.ok,
    action: () -> Unit
) = Snackbar
    .make(view, message, time)
    .setAction(titleAction) { action() }
    .show()

// Keyboard
fun Fragment.inputMethodManager() =
    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

fun Fragment.hideKeyboard(view: View? = activity?.window?.decorView?.rootView) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) view?.hideKeyboard(view)
    else inputMethodManager()?.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
}

// Animations
fun Fragment.startColorAnimation(view: TextView, fromColor: Int, toColor: Int) {
    ObjectAnimator.ofInt(
        view,
        "textColor",
        fromColor,
        toColor
    ).apply {
        duration = 6000
        setEvaluator(ArgbEvaluator())
        repeatMode = ValueAnimator.REVERSE
        repeatCount = ValueAnimator.REVERSE
        start()
    }
}

// Navigation
fun Fragment.navigateBack(@IdRes destination: Int) = findNavController().popBackStack(destination, true)
fun Fragment.navigateTo(@IdRes destination: Int) = findNavController().navigate(destination)
fun Fragment.navigateTo(directions: NavDirections) = findNavController().navigate(directions)
fun Fragment.navigateTo(@IdRes destination: Int, args: Bundle) =
    findNavController().navigate(destination, args)
fun Fragment.navigateTo(directions: NavDirections, args: FragmentNavigator.Extras) =
    findNavController().navigate(directions, args)

// Permissions
fun Fragment.hasPermission(permission: String): Boolean {
    val permissionCheckResult = ContextCompat.checkSelfPermission(requireContext(), permission)

    return PackageManager.PERMISSION_GRANTED == permissionCheckResult
}

fun Fragment.hasPermissions(permissions: Array<String>): Boolean {
    val granted = mutableListOf<Boolean>()

    permissions.forEach { granted.add(hasPermission(it)) }

    return granted.all { it }
}

fun Fragment.shouldRequestPermission(permissions: Array<String>): Boolean {
    val granted = mutableListOf<Boolean>()

    permissions.forEach { granted.add(hasPermission(it)) }

    return granted.any { it.not() }
}
