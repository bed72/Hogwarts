package com.bed.seller.presentation.ui.splash

import kotlinx.coroutines.launch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.databinding.SplashFragmentBinding

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.fragments.BaseFragment
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding>(SplashFragmentBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeStates()
    }

    private fun observeStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        States.Loading -> {}
                        is States.Success -> navigateTo(SplashFragmentDirections.actionSplashToHome())
                        is States.Failure -> {
                            state.consumed = true
                            navigateTo(SplashFragmentDirections.actionSplashToSignIn())
                        }
                    }
                }
            }
        }
    }
}
