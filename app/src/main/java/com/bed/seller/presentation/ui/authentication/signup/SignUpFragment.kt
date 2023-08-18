package com.bed.seller.presentation.ui.authentication.signup

import android.os.Bundle
import android.view.View

import com.bed.seller.databinding.SignUpFragmentBinding

import com.bed.seller.presentation.commons.fragments.BaseFragment
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo

class SignUpFragment : BaseFragment<SignUpFragmentBinding>(SignUpFragmentBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        setupSingUpButton()
        setupSignInButton()
    }

    private fun setupSingUpButton() {
        binding.signUpButton.setOnClickListener {
            navigateTo(SignUpFragmentDirections.actionSingUpToHome())
        }
    }

    private fun setupSignInButton() {
        binding.signUpAlreadyExistingAccountButton.setOnClickListener {
            navigateTo(SignUpFragmentDirections.actionSignUpToSignIn())
        }
    }
}
