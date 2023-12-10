package com.bed.seller.presentation.ui.authentication.signout

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R
import com.bed.seller.databinding.ExitFragmentBinding

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.lifecycleExecute
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class SignOutFragment : BaseBottomSheetDialogFragment<ExitFragmentBinding>(
    ExitFragmentBinding::inflate
) {
    private val viewModel: SignOutViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeSignOutState()
    }

    private fun observeSignOutState() {
        lifecycleExecute {
            viewModel.state.collect { state ->
                binding.actionFlipper.displayedChild = when (state) {
                    States.Initial -> INITIAL
                    States.Loading -> LOADING
                    is States.Failure -> failureSignOut()
                    is States .Success -> successSignOut()
                }
            }
        }
    }

    private fun setupComponents() {
        setupButtons()
        setupDescription()
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
        navigateTo(SignOutFragmentDirections.actionSignOutToSignIn())

        return INITIAL
    }

    companion object {
        const val LOADING = 1
        const val INITIAL = 0
    }
}
