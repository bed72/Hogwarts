package com.bed.seller.presentation.ui.splash

import android.os.Bundle
import android.view.View

import com.bed.seller.databinding.SplashFragmentBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

import com.bed.seller.presentation.extensions.navigationTo
import com.bed.seller.presentation.extensions.snake
import com.bed.seller.presentation.ui.auth.user.UserViewModel
import com.bed.seller.presentation.ui.common.fragment.BaseFragment
import com.bed.seller.presentation.ui.auth.user.states.UserLiveData

class SplashFragment : BaseFragment<SplashFragmentBinding>(SplashFragmentBinding::inflate) {

    private val userViewModel: UserViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserState()
    }

    private fun observeUserState() {
        with (userViewModel.user) {
            get()

            state.observe(viewLifecycleOwner) { states ->
                when (states) {
                    UserLiveData.States.Loading -> {}
                    is UserLiveData.States.Success ->
                        if (states.data.userMetadata.name.isNotEmpty())
                            navigationTo(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                        else navigationTo(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
                    is UserLiveData.States.Failure -> {
                        snake(requireView(), states.message)

                        navigationTo(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
                    }
                }
            }
        }
    }
}
