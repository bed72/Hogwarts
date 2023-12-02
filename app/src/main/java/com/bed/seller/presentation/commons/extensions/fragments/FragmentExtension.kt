package com.bed.seller.presentation.commons.extensions.fragments

import android.os.Build
import android.content.Context

import android.view.View
import android.view.inputmethod.InputMethodManager

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bed.seller.presentation.commons.extensions.hideKeyboard
import kotlinx.coroutines.launch

fun Fragment.inputMethodManager() =
    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

fun Fragment.hideKeyboard(view: View? = activity?.window?.decorView?.rootView) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) view?.hideKeyboard(view)
    else inputMethodManager()?.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
}

fun Fragment.openExternalApp(app: String, context: Context? = this.requireContext()) {
    context?.let {
        it.startActivity(it.packageManager.getLaunchIntentForPackage(app))
    }
}

fun Fragment.lifecycleExecute(state: Lifecycle.State = Lifecycle.State.STARTED, execute: suspend () -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            execute()
        }
    }
}
