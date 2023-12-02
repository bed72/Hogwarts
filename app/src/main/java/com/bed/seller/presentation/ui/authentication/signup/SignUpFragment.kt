package com.bed.seller.presentation.ui.authentication.signup

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.core.domain.parameters.authentication.AuthenticationParameter

import com.bed.seller.databinding.SignUpFragmentBinding

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
class SignUpFragment : BaseFragment<SignUpFragmentBinding>(SignUpFragmentBinding::inflate) {

    private var emailRow = ""
    private var passwordRow = ""

    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeFormState()
        observeSignUpState()
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

    private fun observeSignUpState() {
        viewModel.states.observe(viewLifecycleOwner) { states ->
            when (states) {
                SignUpViewModel.States.Loading -> handlerLoading(ConstantStates.VISIBLE, ConstantStates.GONE)
                is SignUpViewModel.States.Failure -> {
                    snackBar(states.data)
                    handlerLoading(ConstantStates.GONE, ConstantStates.VISIBLE)
                }
                is SignUpViewModel.States.Success -> {
                    snackBar(R.string.sign_up_success_message)
                    navigateTo(SignUpFragmentDirections.actionSingUpToHome())
                }
            }
        }
    }

    private fun setupComponents() {
        setupForm()
        setupButtons()
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
            signUpButton.setOnClickListener { validateParameter() }

            alreadyExistingAccountButton.setOnClickListener {
                navigateTo(SignUpFragmentDirections.actionSignUpToSignIn())
            }
        }
    }

    private fun validateParameter() {
        hideKeyboard()

        AuthenticationParameter(emailRow, passwordRow).fold(
            { _ -> snackBar(R.string.generic_failures_form) },
            { success -> viewModel.signUp(success) }
        )
    }

    private fun handlerLoading(progressVisibility: Int, buttonVisibility: Int) {
        with (binding) {
            progress.visibility = progressVisibility
            signUpButton.visibility = buttonVisibility
        }
    }
}
