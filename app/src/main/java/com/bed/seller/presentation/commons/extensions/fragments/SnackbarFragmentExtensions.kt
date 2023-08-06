package com.bed.seller.presentation.commons.extensions.fragments

import android.view.View

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

import com.google.android.material.snackbar.Snackbar

fun Fragment.snackbar(view: View, message: String) =
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

fun Fragment.snackbar(view: View, @StringRes message: Int, time: Int = 6000) =
    Snackbar.make(view, getText(message), time).setDuration(time).show()

fun Fragment.snakeArg(view: View, @StringRes message: Int, arg: String) =
    Snackbar.make(view, resources.getString(message, arg), Snackbar.LENGTH_LONG).show()

fun Fragment.snackbar(
    view: View,
    message: String,
    time: Int = Snackbar.LENGTH_INDEFINITE,
    @StringRes titleAction: Int = android.R.string.ok,
    action: () -> Unit
) = Snackbar
    .make(view, message, time)
    .setAction(titleAction) { action() }
    .show()
