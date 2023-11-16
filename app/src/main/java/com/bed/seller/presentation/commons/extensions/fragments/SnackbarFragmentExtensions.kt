package com.bed.seller.presentation.commons.extensions.fragments

import android.view.View

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.BaseTransientBottomBar

import com.bed.seller.R

fun Fragment.snackBar(view: View, message: String) =
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

fun Fragment.snackBar(view: View, @StringRes message: Int, time: Int = 6000) =
    Snackbar.make(view, getText(message), time).setDuration(time).show()

fun Fragment.snackBar(view: View, @StringRes message: Int, arg: String) =
    Snackbar.make(view, resources.getString(message, arg), Snackbar.LENGTH_LONG).show()

fun Fragment.snackBar(
    view: View,
    message: String,
    time: Int = Snackbar.LENGTH_INDEFINITE,
    @StringRes titleAction: Int = android.R.string.ok,
    action: () -> Unit
) = Snackbar
    .make(view, message, time)
    .setAction(titleAction) { action() }
    .show()

fun Fragment.snackBar(
    view: View,
    message: String,
    action: () -> Unit
) = Snackbar
    .make(view, message, Snackbar.LENGTH_LONG)
    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) { action() }
    })
    .setAction(R.string.exit_snackbar) { action() }
    .show()
