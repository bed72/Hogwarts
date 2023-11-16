package com.bed.seller.presentation.ui.authentication.signup

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.SignUpFragmentBinding

import com.bed.core.domain.parameters.authentication.SignUpParameter
import com.bed.core.values.getFirstMessage

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.NameState
import com.bed.seller.presentation.commons.states.EmailState
import com.bed.seller.presentation.commons.states.PasswordState

import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.extensions.actionKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpFragmentBinding>(SignUpFragmentBinding::inflate) {

    private var nameRow = ""
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
        with (viewModel) {
            name.states.observe(viewLifecycleOwner) { states ->
                when (states) {
                    is NameState.States.Failure -> binding.nameTextInput.error = states.data
                    is NameState.States.Success -> {
                        nameRow = states.data()
                        binding.nameTextInput.helperText = getString(R.string.sign_up_valid_name, nameRow)
                    }
                }
            }
            email.states.observe(viewLifecycleOwner) { states ->
                when (states) {
                    is EmailState.States.Failure -> binding.emailTextInput.error = states.data
                    is EmailState.States.Success -> {
                        emailRow = states.data()
                        binding.emailTextInput.helperText = getString(R.string.sign_up_valid_email, emailRow)
                    }
                }
            }
            password.states.observe(viewLifecycleOwner) { states->
                when (states) {
                    is PasswordState.States.Failure -> binding.passwordTextInput.error = states.data
                    is PasswordState.States.Success -> {
                        passwordRow = states.data()
                        binding.passwordTextInput.helperText = getString(R.string.sign_up_valid_password)
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
                    snackBar(requireView(), states.data)
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
            nameEditInput.debounce { viewModel.name.set(it) }
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
        SignUpParameter(nameRow, emailRow, passwordRow).fold(
            { failure -> snackBar(requireView(), failure.getFirstMessage()) },
            { success -> viewModel.signUp(success) }
        )
    }
}
