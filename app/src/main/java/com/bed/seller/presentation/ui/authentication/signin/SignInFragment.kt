package com.bed.seller.presentation.ui.authentication.signin

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback

import com.bed.seller.databinding.SignInFragmentBinding

import com.bed.seller.presentation.commons.fragments.BaseFragment
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo

class SignInFragment : BaseFragment<SignInFragmentBinding>(SignInFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        setupExitApp()
        setupSingInButton()
        setupSignUpButton()
        setupRecoverAccountButton()
    }

    private fun setupExitApp() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateTo(SignInFragmentDirections.actionSignInToExit())
        }
    }

    private fun setupSingInButton() {
        binding.signInButton.setOnClickListener {
            navigateTo(SignInFragmentDirections.actionSignInToHome())
        }
    }

    private fun setupSignUpButton() {
        binding.createAccountButton.setOnClickListener {
            navigateTo(SignInFragmentDirections.actionSignInToSignUp())
        }
    }

    private fun setupRecoverAccountButton() {
        binding.recoverPasswordButton.setOnClickListener {
            navigateTo(SignInFragmentDirections.actionSignInToRecover())
        }
    }

}
