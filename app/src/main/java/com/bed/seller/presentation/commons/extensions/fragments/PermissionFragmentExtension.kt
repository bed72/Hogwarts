package com.bed.seller.presentation.commons.extensions.fragments

import android.content.pm.PackageManager

import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat

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
