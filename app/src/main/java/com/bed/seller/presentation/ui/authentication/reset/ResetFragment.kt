package com.bed.seller.presentation.ui.authentication.reset

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.core.values.StringValue

import com.bed.seller.databinding.ResetFragmentBinding

import com.bed.core.domain.parameters.authentication.ResetParameter

import com.bed.seller.presentation.commons.states.PasswordState
import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.extensions.actionKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.snackbar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard

@AndroidEntryPoint
class ResetFragment : BaseFragment<ResetFragmentBinding>(ResetFragmentBinding::inflate) {
    private var parameter = ResetParameter()

    private val viewModel: ResetViewModel by viewModels()

    private val arguments by navArgs<ResetFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeFormState()
        observeResetState()

        parameter = parameter.copy(code = StringValue(handleCode(arguments.code)))
    }

    private fun observeFormState() {
        with (viewModel) {
            password.states.observe(viewLifecycleOwner) { states ->
                when (states) {
                    is PasswordState.States.Failure -> binding.passwordTextInput.error = states.data
                    is PasswordState.States.Success -> {
                        parameter = parameter.copy(password = states.data)
                        binding.passwordTextInput.helperText = getString(R.string.valid_password)
                    }
                }
            }
            repeatPassword.states.observe(viewLifecycleOwner) { states ->
                when (states) {
                    is PasswordState.States.Failure -> binding.repeatPasswordTextInput.error = states.data
                    is PasswordState.States.Success -> {
                        parameter = parameter.copy(repeatPassword = states.data)
                        binding.repeatPasswordTextInput.helperText = getString(R.string.valid_password)
                    }
                }
            }
        }
    }

    private fun observeResetState() {
        viewModel.states.observe(viewLifecycleOwner) { state ->
            binding.actionFlipper.displayedChild = when (state) {
                ResetViewModel.States.Loading -> States.FLIPPER_LOADING
                is ResetViewModel.States.Reset ->
                    if (state.isSuccess) handlerSuccessMessage() else handlerFailureMessage()
            }
        }
    }

    private fun setupComponents() {
        setupForm()
        setupButtons()
    }

    private fun setupForm() {
        with (binding) {
            passwordEditInput.debounce { viewModel.password.set(it) }
            repeatPasswordEditInput.debounce { viewModel.repeatPassword.set(it) }
            repeatPasswordEditInput.actionKeyboard { validateParameter() }
        }
    }

    private fun setupButtons() {
        binding.resetButton.setOnClickListener { validateParameter()  }
    }

    private fun handleCode(code: String?): String =
        code?.let {
            val pattern = "oobCode=([^&]+)&".toRegex()

            val match = pattern.find(it)

            match?.groupValues?.get(1) ?: ""
        } ?: ""

    private fun validateParameter() {
        hideKeyboard(binding.root)
        parameter.isValid().fold(
            { failure -> snackbar(failure[SECOND_MESSAGE]) },
            { success -> viewModel.reset(success) }
        )
    }

    private fun handlerFailureMessage(): Int {
        snackbar(R.string.generic_failure_title)

        return States.FLIPPER_FAILURE
    }

    private fun handlerSuccessMessage(): Int {
        snackbar(R.string.reset_success_title)

        navigateTo(ResetFragmentDirections.actionRecoverToSignIn())

        return States.FLIPPER_SUCCESS
    }

    companion object {
        private const val SECOND_MESSAGE = 1
    }
}
