package com.bed.seller.presentation.ui.authentication.reset

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.activity.OnBackPressedCallback

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.ResetFragmentBinding

import com.bed.seller.presentation.commons.states.PasswordState
import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.core.values.getFirstMessage
import com.bed.core.domain.parameters.authentication.ResetParameter

import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.states.ConstantStates
import com.bed.seller.presentation.commons.extensions.actionKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard

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

        codeRow = handleCode(arguments.code)
    }

    private fun observeFormState() {
        with (viewModel) {
            password.states.observe(viewLifecycleOwner) { states ->
                when (states) {
                    is PasswordState.States.Failure -> {
                        passwordRow = ""
                        binding.passwordTextInput.error = states.data
                    }
                    is PasswordState.States.Success -> {
                        passwordRow = states.data()
                        binding.passwordTextInput.helperText = getString(R.string.valid_password)
                    }
                }
            }
        }
    }

    private fun observeResetState() {
        viewModel.states.observe(viewLifecycleOwner) { state ->
            binding.actionFlipper.displayedChild = when (state) {
                ResetViewModel.States.Loading -> ConstantStates.FLIPPER_LOADING
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
        with (binding.passwordEditInput) {
            debounce { viewModel.password.set(it) }
            actionKeyboard { validateParameter() }
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

        ResetParameter(codeRow, passwordRow).fold(
            { failure -> snackBar(failure.getFirstMessage()) },
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
