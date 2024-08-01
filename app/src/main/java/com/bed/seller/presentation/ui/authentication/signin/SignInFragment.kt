package com.bed.seller.presentation.ui.authentication.signin

import android.util.Log
import android.os.Bundle
import android.view.View
import android.content.ContentValues.TAG

import androidx.activity.addCallback
import androidx.fragment.app.viewModels

import kotlinx.coroutines.coroutineScope

import dagger.hilt.android.AndroidEntryPoint

import androidx.credentials.CustomCredential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

import com.bed.seller.R

import com.bed.seller.databinding.SignInFragmentBinding

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.FormState
import com.bed.seller.presentation.commons.states.ConstantStates
import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.extensions.actionKeyboard
import com.bed.seller.presentation.commons.extensions.observePairStates
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.lifecycleExecute
import com.google.android.libraries.identity.googleid.GetGoogleIdOption

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInFragmentBinding>(SignInFragmentBinding::inflate) {

    private var emailRow = ""
    private var passwordRow = ""

    private val viewModel: SignInViewModel by viewModels()

    private lateinit var credentialManager: CredentialManager
    private lateinit var credentialRequest: GetCredentialRequest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val googleOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(getString(R.string.credendials))
            .setAutoSelectEnabled(true)
            .build()

//            GetSignInWithGoogleOption
//            .Builder(getString(R.string.credendials))
//            .build()

        credentialManager = CredentialManager.create(requireContext())
        credentialRequest = GetCredentialRequest
            .Builder()
            .addCredentialOption(googleOption)
            .build()

        setupComponents()

        observeFormState()
        observeSignInState()
    }

    private fun observeFormState() {
        lifecycleExecute {
            viewModel.email.state.collect {
                it.observePairStates(
                    failure = { data ->
                        emailRow = ""
                        binding.emailTextInput.error = data
                    },
                    success = { data ->
                        emailRow = data
                        binding.emailTextInput.helperText = getString(R.string.valid_email, emailRow)
                    }
                )
            }
        }

        lifecycleExecute {
            viewModel.password.state.collect {
                it.observePairStates(
                    failure = { data ->
                        passwordRow = ""
                        binding.passwordTextInput.error = data
                    },
                    success = { data ->
                        passwordRow = data
                        binding.passwordTextInput.helperText = getString(R.string.valid_password)
                    }
                )
            }
        }
    }

    private fun observeSignInState() {
        lifecycleExecute {
            viewModel.state.collect { state ->
                binding.actionFlipper.displayedChild = when (state) {
                    States.Initial -> ConstantStates.FLIPPER_SUCCESS
                    States.Loading -> ConstantStates.FLIPPER_LOADING
                    is States.Failure -> {
                        snackBar(requireView(), state.data)
                        ConstantStates.FLIPPER_FAILURE
                    }
                    is States.Success -> {
                        navigateTo(SignInFragmentDirections.actionSignInToHome())
                        ConstantStates.FLIPPER_SUCCESS
                    }
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

        singInWithGoogle()
//        AuthenticationInput(emailRow, passwordRow).fold(
//            { _ -> snackBar(R.string.generic_failures_form) },
//            { success -> viewModel.signIn(success) }
//        )
    }

    private fun singInWithGoogle() {
        lifecycleExecute {
            coroutineScope {
                try {
                    val result = credentialManager.getCredential(
                        context = requireContext(),
                        request = credentialRequest,
                    )
                    handleSignIn(result)
                } catch (exception: Exception) {
                    Log.e(TAG, "Unexpected type of credential", exception)
                }
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        Log.e(TAG, "NAME ${googleIdTokenCredential.displayName}")

                    } catch (exception: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", exception)
                    }
                } else Log.e(TAG, "Unexpected type of credential")
            } else -> Log.e(TAG, "Unexpected type of credential")
        }
    }
}
