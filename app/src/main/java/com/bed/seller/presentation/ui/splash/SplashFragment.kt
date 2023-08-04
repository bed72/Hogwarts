package com.bed.seller.presentation.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bed.seller.databinding.SplashFragmentBinding
import com.bed.seller.presentation.commons.extensions.navigateTo
import com.bed.seller.presentation.commons.fragments.BaseFragment
import com.bed.seller.presentation.ui.splash.states.SplashGetTokenLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding>(SplashFragmentBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeStates()
    }

    private fun observeStates() {
        with(viewModel.get) {
            accessToken("DATA_STORE_ACCESS_TOKEN")

            state.observe(viewLifecycleOwner) { state ->
                when (state) {
                    SplashGetTokenLiveData.States.Success ->
                        navigateTo(SplashFragmentDirections.actionSplashToHome())
                    SplashGetTokenLiveData.States.Failure ->
                        navigateTo(SplashFragmentDirections.actionSplashToSignIn())
                }
            }
        }
    }
}
