package com.bed.seller.presentation.ui.authentication.recover

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.RecoverFragmentBinding

import com.bed.seller.presentation.commons.constants.AppConstants
import com.bed.seller.presentation.commons.extensions.actionKeyboard

import com.bed.core.values.getFirstMessage
import com.bed.core.domain.parameters.authentication.RecoverParameter

import com.bed.seller.presentation.commons.states.EmailState
import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.states.ConstantStates
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.openExternalApp
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class RecoverFragment : BaseBottomSheetDialogFragment<RecoverFragmentBinding>(
    RecoverFragmentBinding::inflate
) {
    private var emailRow = ""

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
                is EmailState.States.Failure -> {
                    emailRow = ""
                    binding.emailTextInput.error = states.data
                }
                is EmailState.States.Success -> {
                    emailRow = states.data()
                    binding.emailTextInput.helperText = getString(R.string.valid_email, states.data())
                }
            }
        }
    }

    private fun observeRecoverStates() {
        viewModel.states.observe(viewLifecycleOwner) { states ->
            when (states) {
                RecoverViewModel.States.Loading -> handlerLoading(ConstantStates.VISIBLE, ConstantStates.GONE)
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

        RecoverParameter(emailRow).fold(
            { failure -> snackBar(failure.getFirstMessage()) },
            { success -> viewModel.recover(success) }
        )
    }

    private fun handlerLoading(progressVisibility: Int, containerVisibility: Int) {
        with (binding) {
            progress.visibility = progressVisibility
            containerButtons.visibility = containerVisibility
        }
    }

    private fun handlerFailureMessage() {
        snackBar(R.string.generic_failure_title)
        handlerLoading(ConstantStates.GONE, ConstantStates.VISIBLE)
    }

    private fun handlerSuccessMessage() {
        snackBar(R.string.recover_success_title, R.string.recover_open_email_title_button, ::dismiss, ::navigateToEmail)
    }

    private fun navigateToEmail() {
        dismiss()

        openExternalApp(AppConstants.EMAIL_APP)
    }
}
