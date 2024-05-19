package com.bed.seller.presentation.ui.authentication.recover

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.core.entities.input.RecoverInput

import com.bed.seller.R

import com.bed.seller.databinding.RecoverFragmentBinding

import com.bed.seller.presentation.commons.states.FormState
import com.bed.seller.presentation.commons.states.ConstantStates
import com.bed.seller.presentation.commons.constants.AppConstants
import com.bed.seller.presentation.commons.extensions.actionKeyboard

import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.extensions.observePairStates
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.extensions.observeTripleStates
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.openExternalApp
import com.bed.seller.presentation.commons.extensions.fragments.lifecycleExecute

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
    }

    private fun observeRecoverStates() {
        lifecycleExecute {
            viewModel.state.collect {
                it.observeTripleStates(
                    loading = { handlerLoading(ConstantStates.VISIBLE, ConstantStates.GONE) },
                    failure = { handlerFailureMessage() },
                    success = { isSuccess ->
                        if (isSuccess) handlerSuccessMessage() else handlerFailureMessage()
                    }
                )
            }
        }
    }

    private fun setupComponents() {
        setupForm()
        setupButtons()
    }

    private fun setupForm() {
        with (binding.emailEditInput) {
            debounce { viewModel.email.set(it, FormState.Type.Email) }
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

        RecoverInput(emailRow).fold(
            { _ -> snackBar(R.string.generic_email_failure_form) },
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
