package com.bed.seller.presentation.ui.authentication.signup

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.SignUpFragmentBinding

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.EmailState
import com.bed.seller.presentation.commons.states.PasswordState

import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.extensions.actionKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.snackbar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard

import com.bed.core.domain.parameters.authentication.AuthenticationParameter

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpFragmentBinding>(SignUpFragmentBinding::inflate) {

    private var parameter = AuthenticationParameter()

    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeFormState()
        observeSignUpState()
    }

    private fun observeFormState() {
        with (viewModel) {
            email.states.observe(viewLifecycleOwner) { states ->
                when (states) {
                    is EmailState.States.Failure -> binding.emailTextInput.error = states.data
                    is EmailState.States.Success -> {
                        parameter = parameter.copy(email = states.data)
                        binding.emailTextInput.helperText =
                            getString(R.string.sign_up_valid_email, states.data.value)
                    }
                }
            }
            password.states.observe(viewLifecycleOwner) { states->
                when (states) {
                    is PasswordState.States.Failure -> binding.passwordTextInput.error = states.data
                    is PasswordState.States.Success -> {
                        parameter = parameter.copy(password = states.data)
                        binding.passwordTextInput.helperText =
                            getString(R.string.sign_up_valid_password)
                    }
                }
            }
        }
    }

    private fun observeSignUpState() {
        viewModel.states.observe(viewLifecycleOwner) { states ->
            binding.actionFlipper.displayedChild = when (states) {
                SignUpViewModel.States.Loading -> States.FLIPPER_LOADING
                is SignUpViewModel.States.Failure -> {
                    snackbar(requireView(), states.data)
                    States.FLIPPER_FAILURE
                }
                is SignUpViewModel.States.Success -> {
                    navigateTo(SignUpFragmentDirections.actionSingUpToHome())
                    States.FLIPPER_SUCCESS
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
            emailEditInput.debounce { viewModel.email.set(it) }
            passwordEditInput.debounce { viewModel.password.set(it) }
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
        parameter.isValid().fold(
            { failure -> snackbar(requireView(), failure[States.FIRST_MESSAGE]) },
            { success -> viewModel.signUp(success) }
        )
    }
}
