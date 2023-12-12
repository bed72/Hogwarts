package com.bed.seller.presentation.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.databinding.SplashFragmentBinding
import com.bed.seller.presentation.commons.extensions.fragments.lifecycleExecute

import com.bed.seller.presentation.commons.fragments.BaseFragment
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding>(SplashFragmentBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoggedIn()

        observeStates()
    }

    private fun observeStates() {
        lifecycleExecute {
            viewModel.state.collect {
                if (it is SplashViewModel.States.IsLoggedIn && it.isLogged)
                    navigateTo(SplashFragmentDirections.actionSplashToHome())
                else navigateTo(SplashFragmentDirections.actionSplashToSignIn())
            }
        }
    }
}
