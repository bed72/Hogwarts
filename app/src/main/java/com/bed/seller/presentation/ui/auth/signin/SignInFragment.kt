package com.bed.seller.presentation.ui.auth.signin

import com.bed.seller.R

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes

import com.bed.seller.databinding.SignInFragmentBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

import com.google.android.material.textfield.TextInputLayout

import com.bed.seller.presentation.extensions.snake
import com.bed.seller.presentation.extensions.hideKeyboard
import com.bed.seller.presentation.extensions.navigationTo
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
                Auth.States.Empty -> Auth.EMPTY
                Auth.States.Loading -> Auth.LOADING
                is Auth.States.Success -> {
                    snake(requireView(), states.message)
                    navigationTo(R.id.action_sign_in_fragment_to_home_fragment)

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
                    setupActionSignInButton(state)
                }
            }
        }
    }

    private fun setupComponents() {
        setupForm()
        setupSignUpButton()
        setupRecoverAccountButton()
    }

    private fun setupForm() {
        with(binding) {
            signInEmailEditInput.getTextChanged { text -> viewModel.email.set(text)}
            signInPasswordEditInput.getTextChanged { text -> viewModel.password.set(text) }
        }
    }

    private fun setupSignUpButton() {
        binding.signInCreateAccountButton.setOnClickListener {
            navigationTo(R.id.action_sign_in_fragment_to_sign_up_fragment)
        }
    }

    private fun setupRecoverAccountButton() {
        binding.signInRecoverAccountButton.setOnClickListener {
            navigationTo(SignInFragmentDirections.actionSignInFragmentToRecoverAccountFragment())
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
    ) { input.error = if (isClean) Auth.CLEAR else getString(message) }

    private fun setupActionKeyboard(state: Boolean) {
        binding.signInPasswordEditInput.actionKeyboard { doSignIn(state) }
    }

    private fun setupActionSignInButton(state: Boolean = false) {
        binding.signInButton.setOnClickListener { doSignIn(state) }
    }

    private fun doSignIn(state: Boolean) {
        if (state and authBody.isNotEmpty()) viewModel.submit(authBody)
         else snake(requireView(), R.string.sign_up_generic_error)

        hideKeyboard()
    }

    private fun buttonIsEnabled(isEnabled: Boolean = false) {
        binding.signInButton.isEnabled = isEnabled
    }
}
