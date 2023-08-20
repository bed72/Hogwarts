package com.bed.seller.presentation.ui.splash

import androidx.fragment.app.viewModels

import android.os.Bundle
import android.view.View

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.databinding.SplashFragmentBinding

import com.bed.seller.framework.constants.StorageConstant

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
            getAccessToken(StorageConstant.DATASTORE_REFRESH_TOKEN.value)

            states.observe(viewLifecycleOwner) { state ->
                when (state) {
                    SplashViewModel.States.Success ->
                        navigateTo(SplashFragmentDirections.actionSplashToHome())
                    SplashViewModel.States.Failure ->
                        navigateTo(SplashFragmentDirections.actionSplashToSignIn())
                }
            }
        }
    }
}
