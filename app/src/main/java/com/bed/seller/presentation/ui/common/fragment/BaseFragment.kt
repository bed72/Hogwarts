package com.bed.seller.presentation.ui.common.fragment

import android.os.Bundle
import android.util.Log

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

import android.content.ContentValues.TAG

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<viewBinding : ViewBinding>(
    private val inflate: Inflate<viewBinding>
) : Fragment() {

    private var _binding: viewBinding? = null
    protected val binding: viewBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "FRAGMENT - ON_CREATE")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "FRAGMENT - ON_CREATE_VIEW")
        _binding = inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "FRAGMENT - ON_DESTROY")

        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "FRAGMENT - ON_DESTROY_VIEW")

        _binding = null
    }
}
