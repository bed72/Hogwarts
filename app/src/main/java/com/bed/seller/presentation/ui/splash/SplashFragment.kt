package com.bed.seller.presentation.ui.splash

import androidx.fragment.app.viewModels

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.SplashFragmentBinding

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
        with(viewModel) {
            isLoggedIn()

            states.observe(viewLifecycleOwner) { state ->
                when (state) {
                    SplashViewModel.States.Loading -> animate()
                    is SplashViewModel.States.IsLoggedIn -> navigate(state.isSuccess)
                }
            }
        }
    }

    private fun navigate(isSuccess: Boolean) {
        if (isSuccess) navigateTo(SplashFragmentDirections.actionSplashToHome())
        else navigateTo(SplashFragmentDirections.actionSplashToSignIn())
    }

    private fun animate() {
        AnimationUtils.loadAnimation(requireContext(), R.anim.exit_to_right).apply {
            binding.iconImage.startAnimation(this)
        }
    }
}
