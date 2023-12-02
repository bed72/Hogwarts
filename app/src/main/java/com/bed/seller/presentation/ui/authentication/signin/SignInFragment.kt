package com.bed.seller.presentation.ui.authentication.signin

import android.os.Bundle
import android.view.View

import androidx.activity.addCallback
import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.core.domain.parameters.authentication.AuthenticationParameter

import com.bed.seller.databinding.SignInFragmentBinding

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.FormState
import com.bed.seller.presentation.commons.states.ConstantStates

import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.extensions.actionKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.lifecycleExecute

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInFragmentBinding>(SignInFragmentBinding::inflate) {

    private var emailRow = ""
    private var passwordRow = ""

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeFormState()
        observeSignInState()
    }

    private fun observeFormState() {
        lifecycleExecute {
            viewModel.email.state.collect { state ->
                when (state) {
                    is States.Failure -> {
                        emailRow = ""
                        binding.emailTextInput.error = state.data
                    }
                    is States.Success -> {
                        emailRow = state.data
                        binding.emailTextInput.helperText = getString(R.string.valid_email, emailRow)
                    }
                    else -> {}
                }
            }
        }

        lifecycleExecute {
            viewModel.password.state.collect { state ->
                when (state) {
                    is States.Failure -> {
                        passwordRow = ""
                        binding.passwordTextInput.error = state.data
                    }
                    is States.Success -> {
                        passwordRow = state.data
                        binding.passwordTextInput.helperText = getString(R.string.valid_password)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun observeSignInState() {
        viewModel.states.observe(viewLifecycleOwner) { states ->
            binding.actionFlipper.displayedChild = when (states) {
                SignInViewModel.States.Loading -> ConstantStates.FLIPPER_LOADING
                is SignInViewModel.States.Failure -> {
                    snackBar(requireView(), states.data)
                    ConstantStates.FLIPPER_FAILURE
                }
                is SignInViewModel.States.Success -> {
                    navigateTo(SignInFragmentDirections.actionSignInToHome())
                    ConstantStates.FLIPPER_SUCCESS
                }
            }
        }
    }

    private fun setupComponents() {
        setupForm()
        setupExitApp()
        setupButtons()
    }

    private fun setupExitApp() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateTo(SignInFragmentDirections.actionSignInToExit())
        }
    }

    private fun setupForm() {
        with (binding) {
            emailEditInput.debounce { viewModel.email.set(it, FormState.Type.Email) }
            passwordEditInput.debounce { viewModel.password.set(it, FormState.Type.Password) }
            passwordEditInput.actionKeyboard { validateParameter() }
        }
    }

    private fun setupButtons() {
        with (binding) {
            signInButton.setOnClickListener { validateParameter() }
            createAccountButton.setOnClickListener {
                navigateTo(SignInFragmentDirections.actionSignInToSignUp())
            }
            recoverPasswordButton.setOnClickListener {
                navigateTo(SignInFragmentDirections.actionSignInToRecover())
            }
        }
    }

    private fun validateParameter() {
        hideKeyboard()

        AuthenticationParameter(emailRow, passwordRow).fold(
            { _ -> snackBar(R.string.generic_failures_form) },
            { success -> viewModel.signIn(success) }
        )
    }
}
