package com.bed.seller.presentation.ui.common.fragment

import android.os.Bundle

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<viewBinding : ViewBinding>(
    private val inflate: Inflate<viewBinding>
) : Fragment() {

    private var _binding: viewBinding? = null
    protected val binding: viewBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
