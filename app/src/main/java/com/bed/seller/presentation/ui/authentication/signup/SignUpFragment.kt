package com.bed.seller.presentation.ui.authentication.signup

import android.os.Bundle

import android.view.View

import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.SignUpFragmentBinding

import com.bed.core.domain.parameters.authentication.SignUpParameter

import com.bed.seller.presentation.commons.states.NameState
import com.bed.seller.presentation.commons.states.EmailState
import com.bed.seller.presentation.commons.states.PasswordState

import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.extensions.fragments.snackbar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpFragmentBinding>(SignUpFragmentBinding::inflate) {

    private var parameter = SignUpParameter()

    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeFormState()
        observeSignUpState()
    }

    private fun observeFormState() {
        with (viewModel) {
            name.states.observe(viewLifecycleOwner) { states ->
                when (states) {
                    is NameState.States.Failure -> binding.nameTextInput.error = states.data
                    is NameState.States.Success -> {
                        parameter = parameter.copy(name = states.data)
                        binding.nameTextInput.helperText =
                            getString(R.string.sign_up_valid_name, states.data.value)
                    }
                }
            }
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
                SignUpViewModel.States.Loading -> { FLIPPER_LOADING }
                is SignUpViewModel.States.Failure -> { FLIPPER_FAILURE }
                is SignUpViewModel.States.Success -> { FLIPPER_SUCCESS }
            }
        }
    }

    private fun setupComponents() {
        setupFormInput()
        setupSingUpButton()
        setupSignInButton()
    }

    private fun setupFormInput() {
        with (binding) {
            nameEditInput.debounce { viewModel.name.set(it) }
            emailEditInput.debounce { viewModel.email.set(it) }
            passwordEditInput.debounce { viewModel.password.set(it) }
        }
    }

    private fun setupSingUpButton() {
        binding.signUpButton.setOnClickListener {
            binding.actionFlipper.displayedChild = FLIPPER_LOADING
            validateParameter()
//            navigateTo(SignUpFragmentDirections.actionSingUpToHome())
        }
    }

    private fun setupSignInButton() {
        binding.alreadyExistingAccountButton.setOnClickListener {
            navigateTo(SignUpFragmentDirections.actionSignUpToSignIn())
        }
    }

    private fun validateParameter() {
        parameter.isValid().fold(
            { failure -> snackbar(requireView(), failure[FIRST_MESSAGE]) },
            { success -> viewModel.signUp(success) }
        )
    }

    companion object {
        private const val FIRST_MESSAGE = 0
        private const val FLIPPER_LOADING = 0
        private const val FLIPPER_FAILURE = 1
        private const val FLIPPER_SUCCESS = FLIPPER_FAILURE
    }
}
