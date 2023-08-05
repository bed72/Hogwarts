package com.bed.seller.presentation.ui.authentication.signin

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback

import com.bed.seller.databinding.SignInFragmentBinding
import com.bed.seller.presentation.commons.extensions.navigateTo

import com.bed.seller.presentation.commons.fragments.BaseFragment

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
        binding.signInCreateAccountButton.setOnClickListener {
            navigateTo(SignInFragmentDirections.actionSignInToSignUp())
        }
    }

    private fun setupRecoverAccountButton() {
        binding.signInRecoverPasswordButton.setOnClickListener {
            navigateTo(SignInFragmentDirections.actionSignInToRecover())
        }
    }

}
