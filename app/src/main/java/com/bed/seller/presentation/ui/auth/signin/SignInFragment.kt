package com.bed.seller.presentation.ui.auth.signin

import com.bed.seller.R

import android.os.Bundle
import android.view.View

import androidx.core.widget.doOnTextChanged

import com.bed.seller.databinding.SignInFragmentBinding
import com.bed.seller.presentation.extensions.navigationTo
import com.bed.seller.presentation.ui.common.fragment.BaseFragment

class SignInFragment : BaseFragment<SignInFragmentBinding>(SignInFragmentBinding::inflate) {

    private var email: String = ""
    private var password: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        signIn()
        setupForm()
        navigateToCreateAccount()
        navigateToRecoverPassword()
    }

    private fun setupForm() {
        with(binding) {
            signInEmailEditInput.doOnTextChanged { text, _, _, _ ->
                email = text.toString()
            }

            signInPasswordEditInput.doOnTextChanged { text, _, _, _ ->
                password = text.toString()
            }
        }
    }

    private fun signIn() {
        binding.signInButton.setOnClickListener {
            // viewModel.validator.valid(SignUpRequestEntity(name, email, password))
        }
    }

    private fun navigateToCreateAccount() {
        binding.signInCreateAccountButton.setOnClickListener {
            navigationTo(R.id.action_sign_in_fragment_to_sign_up_fragment)
        }
    }

    private fun navigateToRecoverPassword() {
        binding.signInRecoverPasswordButton.setOnClickListener {
            navigationTo(R.id.action_sign_in_fragment_to_recover_password_fragment)
        }
    }

//    private fun setupStyleTextFieldEmail() {
//        setupStyleTextField()
//
//        binding.signInEmailTextInput.error = getString(R.string.failure_email_message)
//    }
//
//    private fun setupStyleTextFieldPassword() {
//        setupStyleTextField()
//
//        binding.signInPasswordTextInput.error = getString(R.string.failure_password_message)
//    }
//
//    private fun setupStyleTextField() {
//        with (binding) {
//            signInEmailTextInput.error = ""
//            signInPasswordTextInput.error = ""
//        }
//    }
}
