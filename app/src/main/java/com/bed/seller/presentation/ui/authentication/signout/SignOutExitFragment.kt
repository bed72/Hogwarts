package com.bed.seller.presentation.ui.authentication.signout

import android.os.Bundle
import android.view.View

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.fragment.app.viewModels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.ExitFragmentBinding
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo

import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class SignOutExitFragment : BaseBottomSheetDialogFragment<ExitFragmentBinding>(
    ExitFragmentBinding::inflate
) {
    private val viewModel: SignOutViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeSignOutState()
    }

    private fun observeSignOutState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.actionFlipper.displayedChild = when (state) {
                        SignOutViewModel.States.Initial -> INITIAL
                        SignOutViewModel.States.Loading -> LOADING
                        is SignOutViewModel.States.IsSignOut -> {
                            delay(600)

                            if (state.isSuccess) successSignOut() else failureSignOut()
                        }
                    }
                }
            }
        }
    }

    private fun setupComponents() {
        setupDescription()
        setupButtons()
    }

    private fun setupDescription() {
        binding.descriptionText.text = getText(R.string.exit_description_logout)
    }

    private fun setupButtons() {
        with (binding) {
            noButton.setOnClickListener { dismiss() }
            yesButton.setOnClickListener {
                viewModel.signOut()
            }
        }
    }

    private fun failureSignOut(): Int {
        snackBar(R.string.setting_logout_title_failure)

        return INITIAL
    }

    private fun successSignOut(): Int {
        navigateTo(SignOutExitFragmentDirections.actionSignOutToSignIn())

        return INITIAL
    }

    companion object {
        const val LOADING = 1
        const val INITIAL = 0
    }
}
