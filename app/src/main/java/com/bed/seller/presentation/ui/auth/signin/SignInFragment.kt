package com.bed.seller.presentation.ui.auth.signin

import com.bed.seller.R

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes

import com.bed.seller.databinding.SignInFragmentBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

import com.google.android.material.textfield.TextInputLayout

import com.bed.seller.presentation.extensions.snake
import com.bed.seller.presentation.extensions.navigationTo
import com.bed.seller.presentation.extensions.hideKeyboard
import com.bed.seller.presentation.extensions.getTextChanged
import com.bed.seller.presentation.extensions.actionKeyboard

import com.bed.seller.presentation.ui.auth.commons.Auth
import com.bed.seller.presentation.ui.common.fragment.BaseFragment

import com.bed.seller.domain.entities.form.TextFieldEntity
import com.bed.seller.domain.entities.auth.signin.isNotEmpty
import com.bed.seller.domain.entities.auth.signin.SignInBodyRequestEntity

class SignInFragment : BaseFragment<SignInFragmentBinding>(SignInFragmentBinding::inflate) {

    private var authBody = SignInBodyRequestEntity()
    private val viewModel: SignInViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeSignInState()
        observeSignInFormState()
    }

    private fun observeSignInState() {
        viewModel.auth.state.observe(viewLifecycleOwner) { states ->
            binding.signInActionViewFlipper.displayedChild = when (states) {
                Auth.States.Loading -> Auth.LOADING
                is Auth.States.Success -> {
                    snake(requireView(), states.message)
//                    navigationBack()

                    Auth.SUCCESS
                }
                is Auth.States.Failure -> {
                    snake(requireView(), states.message)

                    Auth.FAILURE
                }
            }
        }
    }

    private fun observeSignInFormState() {
        with (viewModel) {
            email.isValid.observe(viewLifecycleOwner) { states ->
                if (states.valid) {
                    authBody = authBody.copy(email = states.value)
                    setupSuccessMessageInEditInput(states.textField)
                } else {
                    authBody = authBody.copy(email = states.value)
                    setupFailureMessageInEditInput(states.textField)
                    buttonIsEnabled()
                }
            }

            password.isValid.observe(viewLifecycleOwner) { states ->
                if (states.valid) {
                    authBody = authBody.copy(password = states.value)
                    setupSuccessMessageInEditInput(states.textField)
                } else {
                    authBody = authBody.copy(password = states.value)
                    setupFailureMessageInEditInput(states.textField)
                    buttonIsEnabled()
                }
            }

            formIsValid.observe(viewLifecycleOwner) { state ->
                if (state) {
                    setupActionKeyboard(state)
                    setupActionSignInButton(state)
                }
            }
        }
    }

    private fun setupComponents() {
        setupForm()
        setupCreateAccountButton()
        setupRecoverAccountButton()
    }

    private fun setupForm() {
        with(binding) {
            signInEmailEditInput.getTextChanged { text -> viewModel.email.set(text)}
            signInPasswordEditInput.getTextChanged { text -> viewModel.password.set(text) }
        }
    }

    private fun setupCreateAccountButton() {
        binding.signInCreateAccountButton.setOnClickListener {
            navigationTo(R.id.action_sign_in_fragment_to_sign_up_fragment)
        }
    }

    private fun setupRecoverAccountButton() {
        binding.signInRecoverPasswordButton.setOnClickListener {
            navigationTo(R.id.action_sign_in_fragment_to_recover_password_fragment)
        }
    }

    private fun setupSuccessMessageInEditInput(textField: TextFieldEntity?): Boolean {
        textField?.let { setupFailureMessageAtTheForm(it, true) }

        return Auth.FORM_VALID
    }

    private fun setupFailureMessageInEditInput(textField: TextFieldEntity?): Boolean  {
        textField?.let { setupFailureMessageAtTheForm(it) }

        return Auth.FORM_INVALID
    }

    private fun setupFailureMessageAtTheForm(textField: TextFieldEntity, isClean: Boolean = false) {
        when (textField) {
            TextFieldEntity.EMAIL ->
                setupStyleTextField(binding.signInEmailTextInput, R.string.failure_email_message, isClean)
            TextFieldEntity.PASSWORD ->
                setupStyleTextField(binding.signInPasswordTextInput, R.string.failure_password_message, isClean)
            else -> { }
        }
    }

    private fun setupStyleTextField(
        input: TextInputLayout,
        @StringRes message: Int,
        isClean: Boolean = false
    ) { input.error = if (isClean) Auth.EMPTY else getString(message) }

    private fun setupActionKeyboard(state: Boolean) {
        binding.signInPasswordEditInput.actionKeyboard {
            if (state) viewModel.submit(authBody)

            hideKeyboard()
        }
    }

    private fun setupActionSignInButton(state: Boolean = false) {
        with (binding) {
            buttonIsEnabled(true)
            signInButton.setOnClickListener {
                if (state and authBody.isNotEmpty()) viewModel.submit(authBody)
                else snake(requireView(), R.string.sign_up_generic_error)

                hideKeyboard()
            }
        }
    }

    private fun buttonIsEnabled(isEnabled: Boolean = false) {
        binding.signInButton.isEnabled = isEnabled
    }
}
