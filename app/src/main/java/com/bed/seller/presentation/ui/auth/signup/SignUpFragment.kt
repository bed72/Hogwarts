package com.bed.seller.presentation.ui.auth.signup

import com.bed.seller.R

import android.os.Bundle
import android.view.View

import androidx.annotation.StringRes

import com.bed.seller.databinding.SignUpFragmentBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

import com.bed.seller.domain.entities.form.TextFieldEntity

import com.google.android.material.textfield.TextInputLayout

import com.bed.seller.presentation.extensions.snake
import com.bed.seller.presentation.extensions.hideKeyboard
import com.bed.seller.presentation.extensions.navigationTo
import com.bed.seller.presentation.extensions.actionKeyboard
import com.bed.seller.presentation.extensions.getTextChanged
import com.bed.seller.presentation.extensions.navigationBack

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.common.fragment.BaseFragment
import com.bed.seller.presentation.ui.auth.signup.states.SignUpLiveData

import com.bed.seller.domain.entities.auth.signup.isNotEmpty
import com.bed.seller.domain.entities.auth.signup.SignUpBodyRequestEntity

class SignUpFragment : BaseFragment<SignUpFragmentBinding>(SignUpFragmentBinding::inflate) {

    private var authBody = SignUpBodyRequestEntity()

    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeSignUpState()
        observeSignUpFormState()
    }

    private fun observeSignUpState() {
            signUpViewModel.auth.state.observe(viewLifecycleOwner) { states ->
                binding.signUpActionViewFlipper.displayedChild = when (states) {
                    SignUpLiveData.States.Loading -> Commons.LOADING
                    is SignUpLiveData.States.Success -> {
                        snake(requireView(), states.message)
                        navigationTo(R.id.action_sign_up_fragment_to_home_fragment)

                        Commons.SUCCESS
                    }
                    is SignUpLiveData.States.Failure -> {
                        snake(requireView(), states.message)

                        Commons.FAILURE
                    }
                }
            }
    }

    private fun observeSignUpFormState() {
        with (signUpViewModel) {
            name.isValid.observe(viewLifecycleOwner) { states ->
                if (states.valid) {
                    authBody = authBody.copy(name = states.value)
                    setupSuccessMessageInEditInput(states.textField)
                } else {
                    buttonIsEnabled()
                    authBody = authBody.copy(name = states.value)
                    setupFailureMessageInEditInput(states.textField)
                }
            }

            email.isValid.observe(viewLifecycleOwner) { states ->
                if (states.valid) {
                    authBody = authBody.copy(email = states.value)
                    setupSuccessMessageInEditInput(states.textField)
                } else {
                    buttonIsEnabled()
                    authBody = authBody.copy(email = states.value)
                    setupFailureMessageInEditInput(states.textField)
                }
            }

            password.isValid.observe(viewLifecycleOwner) { states ->
                if (states.valid) {
                    authBody = authBody.copy(password = states.value)
                    setupSuccessMessageInEditInput(states.textField)
                } else {
                    buttonIsEnabled()
                    authBody = authBody.copy(password = states.value)
                    setupFailureMessageInEditInput(states.textField)
                }
            }

            formIsValid.observe(viewLifecycleOwner) { state ->
                if (state) {
                    buttonIsEnabled(true)

                    setupActionKeyboard(state)
                    setupActionSignUpButton(state)
                }
            }
        }
    }

    private fun setupComponents() {
        setupForm()
        setupAlreadyExistingAccountButton()
    }

    private fun setupForm() {
        with (binding) {
            signUpNameEditInput.getTextChanged { text -> signUpViewModel.name.set(text) }
            signUpEmailEditInput.getTextChanged { text -> signUpViewModel.email.set(text) }
            signUpPasswordEditInput.getTextChanged { text -> signUpViewModel.password.set(text) }
        }
    }

    private fun setupAlreadyExistingAccountButton() {
        binding.signUpAlreadyExistingAccountButton.setOnClickListener { navigationBack() }
    }

    private fun setupSuccessMessageInEditInput(textField: TextFieldEntity?): Boolean {
        textField?.let { setupFailureMessageAtTheForm(it, true) }

        return Commons.FORM_VALID
    }

    private fun setupFailureMessageInEditInput(textField: TextFieldEntity?): Boolean  {
        textField?.let { setupFailureMessageAtTheForm(it) }

        return Commons.FORM_INVALID
    }

    private fun setupFailureMessageAtTheForm(textField: TextFieldEntity, isClean: Boolean = false) {
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

    private fun setupStyleTextField(
        input: TextInputLayout,
        @StringRes message: Int,
        isClean: Boolean = false
    ) { input.error = if (isClean) Commons.CLEAR else getString(message) }

    private fun setupActionKeyboard(state: Boolean) {
        binding.signUpPasswordEditInput.actionKeyboard {
            if (state) signUpViewModel.submit(authBody)

            hideKeyboard()
        }
    }

    private fun setupActionSignUpButton(state: Boolean = false) {
        binding.signUpButton.setOnClickListener {
            if (state and authBody.isNotEmpty()) signUpViewModel.submit(authBody)
            else snake(requireView(), R.string.sign_up_generic_error)

            hideKeyboard()
        }

    }

    private fun buttonIsEnabled(isEnabled: Boolean = false) {
        binding.signUpButton.isEnabled = isEnabled
    }
}
