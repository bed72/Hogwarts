package com.bed.seller.presentation.ui.auth.signup

import com.bed.seller.R

import android.os.Bundle
import android.view.View

import androidx.annotation.StringRes
import com.bed.seller.databinding.SignUpFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.material.textfield.TextInputLayout

import com.bed.seller.domain.entities.form.TextFieldEntity
import com.bed.seller.domain.entities.auth.AuthBodyRequestEntity
import com.bed.seller.domain.entities.auth.isNotEmpty

import com.bed.seller.presentation.extensions.snake
import com.bed.seller.presentation.extensions.hideKeyboard
import com.bed.seller.presentation.extensions.actionKeyboard
import com.bed.seller.presentation.extensions.navigationBack
import com.bed.seller.presentation.extensions.getTextChanged

import com.bed.seller.presentation.ui.auth.commons.Auth
import com.bed.seller.presentation.ui.common.fragment.BaseFragment

class SignUpFragment : BaseFragment<SignUpFragmentBinding>(SignUpFragmentBinding::inflate) {

    private var signUpData = AuthBodyRequestEntity()
    private val viewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeSignUpFormState()
        observeSignUpState()
    }

    private fun observeSignUpState() {
            viewModel.signUp.state.observe(viewLifecycleOwner) { states ->
                binding.signUpFlipperAction.displayedChild = when (states) {
                    Auth.States.Loading -> LOADING
                    is Auth.States.Success -> {
                        snake(requireView(), states.message)
                        navigationBack()

                        SUCCESS
                    }
                    is Auth.States.Failure -> {
                        snake(requireView(), states.message)

                        FAILURE
                    }
                }
            }
    }

    private fun observeSignUpFormState() {
        with (viewModel) {
            name.isValid.observe(viewLifecycleOwner) { states ->
                if (states.valid) {
                    signUpData = signUpData.copy(name = states.value)
                    setupSuccessMessageInEditInput(states.textField)
                } else {
                    signUpData = signUpData.copy(name = states.value)
                    setupFailureMessageInEditInput(states.textField)
                    buttonIsEnabled()
                }
            }

            email.isValid.observe(viewLifecycleOwner) { states ->
                if (states.valid) {
                    signUpData = signUpData.copy(email = states.value)
                    setupSuccessMessageInEditInput(states.textField)
                } else {
                    signUpData = signUpData.copy(email = states.value)
                    setupFailureMessageInEditInput(states.textField)
                    buttonIsEnabled()
                }
            }

            password.isValid.observe(viewLifecycleOwner) { states ->
                if (states.valid) {
                    signUpData = signUpData.copy(password = states.value)
                    setupSuccessMessageInEditInput(states.textField)
                } else {
                    signUpData = signUpData.copy(password = states.value)
                    setupFailureMessageInEditInput(states.textField)
                    buttonIsEnabled()
                }
            }

            formIsValid.observe(viewLifecycleOwner) { state ->
                if (state) {
                    setupActionSignUpKeyboard(state)
                    setupActionSignUpButton(state)
                }
            }
        }
    }

    private fun setupComponents() {
        setupForm()
        setupAlreadyExistingAccountButton()
    }

    private fun setupAlreadyExistingAccountButton() {
        binding.signUpAlreadyExistingAccountButton.setOnClickListener { navigationBack() }
    }

    private fun setupSuccessMessageInEditInput(textField: TextFieldEntity?): Boolean {
        textField?.let { setupFormFailureMessages(it, true) }

        return FORM_VALID
    }

    private fun setupForm() {
        with (binding) {
            signUpNameEditInput.getTextChanged { text -> viewModel.name.set(text) }
            signUpEmailEditInput.getTextChanged { text -> viewModel.email.set(text) }
            signUpPasswordEditInput.getTextChanged { text -> viewModel.password.set(text) }
        }
    }

    private fun setupFailureMessageInEditInput(textField: TextFieldEntity?): Boolean  {
        textField?.let { setupFormFailureMessages(it) }

        return FORM_INVALID
    }

    private fun setupFormFailureMessages(textField: TextFieldEntity, isClean: Boolean = false) {
        when (textField) {
            TextFieldEntity.NAME ->
                setupStyleTextField(binding.signUpNameTextInput, R.string.failure_name_message, isClean)
            TextFieldEntity.EMAIL ->
                setupStyleTextField(binding.signUpEmailTextInput, R.string.failure_email_message, isClean)
            TextFieldEntity.PASSWORD ->
                setupStyleTextField(binding.signUpPasswordTextInput, R.string.failure_password_message, isClean)
            else -> { }
        }
    }


    private fun setupActionSignUpKeyboard(state: Boolean) {
        binding.signUpPasswordEditInput.actionKeyboard {
            if (state) viewModel.submit(signUpData)

            hideKeyboard()
        }
    }

    private fun setupActionSignUpButton(state: Boolean = false) {
        with (binding) {
            buttonIsEnabled(true)
            signUpButton.setOnClickListener {
                if (state and signUpData.isNotEmpty()) viewModel.submit(signUpData)
                else snake(requireView(), R.string.sign_up_generic_error)

                hideKeyboard()
            }
        }
    }

    private fun buttonIsEnabled(isEnabled: Boolean = false) {
        binding.signUpButton.isEnabled = isEnabled
    }

    private fun setupStyleTextField(
        input: TextInputLayout,
        @StringRes message: Int,
        isClean: Boolean = false
    ) { input.error = if (isClean) EMPTY else getString(message) }

    companion object {
        private const val EMPTY = ""
        private const val LOADING = 1
        private const val SUCCESS = 1
        private const val FAILURE = 2
        private const val FORM_VALID = true
        private const val FORM_INVALID = false
    }
}
