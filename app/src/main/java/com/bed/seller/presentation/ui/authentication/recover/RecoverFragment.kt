package com.bed.seller.presentation.ui.authentication.recover

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.RecoverFragmentBinding

import com.bed.seller.presentation.commons.extensions.actionKeyboard

import com.bed.core.domain.parameters.authentication.RecoverParameter

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.EmailState
import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.constants.AppConstants
import com.bed.seller.presentation.commons.extensions.fragments.snackbar
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.openExternalApp
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class RecoverFragment : BaseBottomSheetDialogFragment<RecoverFragmentBinding>(
    RecoverFragmentBinding::inflate
) {
    private var parameter = RecoverParameter()

    private val viewModel: RecoverViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeFormState()
        observeRecoverStates()
    }

    private fun observeFormState() {
        viewModel.email.states.observe(viewLifecycleOwner) { states ->
            when (states) {
                is EmailState.States.Failure -> binding.emailTextInput.error = states.data
                is EmailState.States.Success -> {
                    parameter = parameter.copy(email = states.data)
                    binding.emailTextInput.helperText = getString(R.string.valid_email, states.data.value)
                }
            }
        }
    }

    private fun observeRecoverStates() {
        viewModel.states.observe(viewLifecycleOwner) { states ->
            when (states) {
                RecoverViewModel.States.Loading -> handlerLoading(States.VISIBLE, States.GONE)
                is RecoverViewModel.States.Recover ->
                    if (states.isSuccess) handlerSuccessMessage() else handlerFailureMessage()
            }
        }
    }

    private fun setupComponents() {
        setupForm()
        setupButtons()
    }

    private fun setupForm() {
        with (binding.emailEditInput) {
            debounce { viewModel.email.set(it) }
            actionKeyboard { validateParameter() }
        }
    }

    private fun setupButtons() {
        with (binding) {
            closeButton.setOnClickListener { dismiss() }
            recoverButton.setOnClickListener { validateParameter() }
        }
    }

    private fun validateParameter() {
        hideKeyboard(binding.root)
        with (parameter.hasMessages()) {
            if (isEmpty()) viewModel.recover(parameter)
            else snackbar(first() ?: getString(R.string.generic_failure_title))
        }
    }

    private fun handlerLoading(progressVisibility: Int, containerVisibility: Int) {
        with (binding) {
            progress.visibility = progressVisibility
            containerButtons.visibility = containerVisibility
        }
    }

    private fun handlerFailureMessage() {
        snackbar(R.string.generic_failure_title)
        handlerLoading(States.GONE, States.VISIBLE)
    }

    private fun handlerSuccessMessage() {
        snackbar(R.string.recover_success_title, R.string.recover_open_email_title_button, ::dismiss, ::navigateToEmail)
    }

    private fun navigateToEmail() {
        dismiss()

        openExternalApp(AppConstants.EMAIL_APP)
    }
}
