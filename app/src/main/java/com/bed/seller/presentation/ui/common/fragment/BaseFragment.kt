package com.bed.seller.presentation.ui.common.fragment

import android.os.Bundle

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

import com.bed.seller.presentation.extensions.navigationTo
import com.bed.seller.presentation.extensions.navigationBack
import com.bed.seller.presentation.ui.common.navigation.command.NavigationCommand

import com.bed.seller.presentation.ui.common.viewmodel.BaseViewModel
import com.bed.seller.presentation.extensions.observeNonNull

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<viewBinding : ViewBinding, viewModel: BaseViewModel>(
    private val inflate: Inflate<viewBinding>
) : Fragment() {

    private var _binding: viewBinding? = null
    protected val binding: viewBinding get() = _binding!!

    protected abstract val viewModel: viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate(inflater, container, false)
        _binding.apply {

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNavigation()
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { navigationCommand ->
                handleNavigation(navigationCommand)
            }
        }
    }

    private fun handleNavigation(navigationCommand: NavigationCommand) {
        when (navigationCommand) {
            NavigationCommand.Back -> navigationBack()
            is NavigationCommand.Navigation -> navigationTo(navigationCommand.direction)
            is NavigationCommand.NavigationDirection -> navigationTo(navigationCommand.directions)
        }
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
