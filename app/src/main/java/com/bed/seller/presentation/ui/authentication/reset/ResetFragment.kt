package com.bed.seller.presentation.ui.authentication.reset

import android.os.Bundle
import android.view.View

import dagger.hilt.android.AndroidEntryPoint

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.activity.OnBackPressedCallback

import com.bed.core.domain.parameters.authentication.ResetParameter

import com.bed.seller.R
import com.bed.seller.databinding.ResetFragmentBinding

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.FormState
import com.bed.seller.presentation.commons.states.ConstantStates
import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.extensions.actionKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.lifecycleExecute

@AndroidEntryPoint
class ResetFragment : BaseFragment<ResetFragmentBinding>(ResetFragmentBinding::inflate) {
    private var codeRow = ""
    private var passwordRow = ""

    private val viewModel: ResetViewModel by viewModels()

    private val arguments by navArgs<ResetFragmentArgs>()

    private val onBackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navigateTo(ResetFragmentDirections.actionResetToSignIn())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressed)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeFormState()
        observeResetState()

        codeRow = arguments.code ?: ""
    }

    private fun observeFormState() {
        lifecycleExecute {
            viewModel.password.state.collect { state ->
                when (state) {
                    is States.Success -> {
                        passwordRow = state.data
                        binding.passwordTextInput.helperText = getString(R.string.valid_password)
                    }
                    is States.Failure -> {
                        passwordRow = ""
                        binding.passwordTextInput.error = state.data
                    }
                    else -> {}
                }
            }
        }
    }

    private fun observeResetState() {
        lifecycleExecute {
            viewModel.state.collect { state ->
                when (state) {
                    States.Loading -> ConstantStates.FLIPPER_LOADING
                    is States.Failure -> handlerFailureMessage()
                    is States.Success -> if (state.data) handlerSuccessMessage() else handlerFailureMessage()
                    else -> {}
                }
            }
        }
    }

    private fun setupComponents() {
        setupForm()
        setupButtons()
    }

    private fun setupForm() {
        with (binding.passwordEditInput) {
            debounce { viewModel.password.set(it, FormState.Type.Password) }
            actionKeyboard { validateParameter() }
        }
    }

    private fun setupButtons() {
        binding.resetButton.setOnClickListener { validateParameter()  }
    }

    private fun validateParameter() {
        hideKeyboard(binding.root)

        if (codeRow.isEmpty()) snackBar(R.string.generic_failure_title)

        ResetParameter(codeRow, passwordRow).fold(
            { _ -> snackBar(R.string.generic_password_failure_form) },
            { success -> viewModel.reset(success) }
        )
    }

    private fun handlerFailureMessage(): Int {
        snackBar(R.string.generic_failure_title)

        return ConstantStates.FLIPPER_FAILURE
    }

    private fun handlerSuccessMessage(): Int {
        snackBar(R.string.reset_success_title)

        navigateTo(ResetFragmentDirections.actionResetToSignIn())

        return ConstantStates.FLIPPER_SUCCESS
    }
}
