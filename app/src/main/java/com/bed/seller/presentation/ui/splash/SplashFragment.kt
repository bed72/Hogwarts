package com.bed.seller.presentation.ui.splash

import android.os.Bundle
import android.view.View

import com.bed.seller.databinding.SplashFragmentBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

import com.bed.seller.presentation.extensions.navigationTo

import com.bed.seller.infrastructure.storage.StorageConstants

import com.bed.seller.presentation.ui.auth.tokens.TokensViewModel
import com.bed.seller.presentation.ui.common.fragment.BaseFragment
import com.bed.seller.presentation.ui.storage.states.GetValueInStorageLiveData

class SplashFragment : BaseFragment<SplashFragmentBinding>(SplashFragmentBinding::inflate) {

    private val viewModel: TokensViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeTokenState()
    }

    private fun observeTokenState() {
        with (viewModel) {
            tokens.get(StorageConstants.DATA_STORE_REFRESH_TOKEN)

            tokens.states.observe(viewLifecycleOwner) { states ->
                when (states) {
                    GetValueInStorageLiveData.States.Loading -> {}
                    is GetValueInStorageLiveData.States.Success ->
                        navigationTo(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                    is GetValueInStorageLiveData.States.Failure ->
                        navigationTo(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
                }
            }
        }
    }
}
