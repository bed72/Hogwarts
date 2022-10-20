package com.bed.seller.presentation.ui.splash

import android.os.Bundle

import android.view.View

import org.koin.androidx.viewmodel.ext.android.viewModel

import com.bed.seller.databinding.SplashFragmentBinding

import com.bed.seller.presentation.extensions.navigationTo

import com.bed.seller.presentation.ui.common.fragment.BaseFragment

import com.bed.seller.domain.entities.auth.tokens.RefreshTokenEntity

import com.bed.seller.presentation.ui.auth.tokens.TokensViewModel
import com.bed.seller.presentation.ui.auth.tokens.states.RefreshTokenLiveData

class SplashFragment : BaseFragment<SplashFragmentBinding>(SplashFragmentBinding::inflate) {

    private val viewModel: TokensViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRefreshTokenState()
    }

    private fun observeRefreshTokenState() {
        with (viewModel.auth) {
            refreshToken(RefreshTokenEntity(""))

            states.observe(viewLifecycleOwner) { states ->
                when (states) {
                    RefreshTokenLiveData.States.Empty, RefreshTokenLiveData.States.Loading -> { }
                    is RefreshTokenLiveData.States.Success ->
                        navigationTo(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                    is RefreshTokenLiveData.States.Failure ->
                        navigationTo(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
                }
            }
        }
    }
}
