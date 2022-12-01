package com.bed.seller.presentation.ui.splash

import android.os.Bundle
import android.view.View

import com.bed.seller.R

import com.bed.seller.databinding.SplashFragmentBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

import com.bed.seller.presentation.extensions.snake
import com.bed.seller.presentation.extensions.navigationTo

import com.bed.seller.presentation.ui.common.fragment.BaseFragment
import com.bed.seller.presentation.ui.splash.states.SplashLiveData

class SplashFragment : BaseFragment<SplashFragmentBinding>(SplashFragmentBinding::inflate) {

    private val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserState()
    }

    private fun observeUserState() {
        with (viewModel.splash) {
            verify()

            state.observe(viewLifecycleOwner) { states ->
                when (states) {
                    SplashLiveData.States.Loading -> {}
                    is SplashLiveData.States.Success ->
                        navigationTo(R.id.action_splash_fragment_to_home_fragment)
                    is SplashLiveData.States.Failure -> {
                        val firstAppOpen = 0
                        if (states.message != firstAppOpen) snake(requireView(), states.message)

                        navigationTo(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
                    }
                }
            }
        }
    }
}
