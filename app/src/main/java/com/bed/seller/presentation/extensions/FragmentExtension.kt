package com.bed.seller.presentation.extensions

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.content.Context
import android.view.inputmethod.InputMethodManager

import com.google.android.material.snackbar.Snackbar

import androidx.annotation.IdRes
import androidx.annotation.StringRes

import androidx.fragment.app.Fragment

import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

// SnackBar
fun Fragment.snake(view: View, @StringRes message: Int) =
    Snackbar.make(view, getText(message), Snackbar.LENGTH_SHORT).show()

// Toast
fun Fragment.showToast(@StringRes message: Int) =
    Toast.makeText(requireContext(), getText(message), Toast.LENGTH_SHORT).show()

// Keyboard
fun Fragment.hideKeyboard(view: View? = activity?.window?.decorView?.rootView) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) view?.hideKeyboard(view)
     else inputMethodManager()?.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
}

fun Fragment.inputMethodManager() =
    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

// Navigation
fun Fragment.navigationBack() = findNavController().navigateUp()
fun Fragment.navigationTo(@IdRes destination: Int) = findNavController().navigate(destination)
fun Fragment.navigationTo(directions: NavDirections) = findNavController().navigate(directions)
fun Fragment.navigationTo(@IdRes destination: Int, args: Bundle) =
    findNavController().navigate(destination, args)
