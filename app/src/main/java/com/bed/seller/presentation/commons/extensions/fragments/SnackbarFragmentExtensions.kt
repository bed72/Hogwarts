package com.bed.seller.presentation.commons.extensions.fragments

import android.view.View

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.BaseTransientBottomBar

import com.bed.seller.R

fun Fragment.snackbar(view: View, message: String) =
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

fun Fragment.snackbar(message: String) =
    Snackbar.make(this.requireView(), message, Snackbar.LENGTH_LONG).show()

fun Fragment.snackbar(@StringRes message: Int, time: Int = 6000) =
    Snackbar.make(this.requireView(), getText(message), time).setDuration(time).show()

fun Fragment.snackbar(@StringRes message: Int, arg: String) =
    Snackbar.make(this.requireView(), resources.getString(message, arg), Snackbar.LENGTH_LONG).show()

fun Fragment.snackbar(
    @StringRes message: Int,
    time: Int = Snackbar.LENGTH_INDEFINITE,
    @StringRes titleAction: Int = android.R.string.ok,
    action: () -> Unit
) = Snackbar
    .make(this.requireView(), message, time)
    .setAction(titleAction) { action() }
    .show()

fun Fragment.snackbar(
    @StringRes message: Int,
    @StringRes titleAction: Int = R.string.exit_snackbar,
    actionClose: () -> Unit,
    actionButton: () -> Unit,
) = Snackbar
    .make(this.requireView(), message, Snackbar.LENGTH_LONG)
    .setAction(titleAction) { actionButton() }
    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) { actionClose() }
    })
    .show()

fun Fragment.snackbar(@StringRes message: Int, action: () -> Unit) = Snackbar
    .make(this.requireView(), message, Snackbar.LENGTH_LONG)
    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) { action() }
    })
    .show()
