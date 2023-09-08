package com.bed.seller.presentation.ui.authentication.reset

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.ResetFragmentBinding

import com.bed.seller.presentation.commons.states.PasswordState
import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.seller.presentation.commons.extensions.debounce
import com.bed.seller.presentation.commons.extensions.actionKeyboard
import com.bed.seller.presentation.commons.extensions.fragments.hideKeyboard


@AndroidEntryPoint
class ResetFragment : BaseFragment<ResetFragmentBinding>(ResetFragmentBinding::inflate) {
    private val viewModel: ResetViewModel by viewModels()

    private val arguments by navArgs<ResetFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeFormState()

        Log.d("CODE", handleCode(arguments.code.toString()))
    }

    private fun observeFormState() {
        with (viewModel) {
            password.states.observe(viewLifecycleOwner) { states->
                when (states) {
                    is PasswordState.States.Failure -> binding.passwordTextInput.error = states.data
                    is PasswordState.States.Success -> {
                        binding.passwordTextInput.helperText =
                            getString(R.string.sign_up_valid_password)
                    }
                }
            }
            repeatPassword.states.observe(viewLifecycleOwner) { states->
                when (states) {
                    is PasswordState.States.Failure -> binding.repeatPasswordTextInput.error = states.data
                    is PasswordState.States.Success -> {
                        binding.passwordTextInput.helperText =
                            getString(R.string.sign_up_valid_password)
                    }
                }
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
            repeatPasswordEditInput.debounce { viewModel.password.set(it) }
            repeatPasswordEditInput.actionKeyboard { validateParameter() }
        }
    }

    private fun setupButtons() {
        binding.resetButton.setOnClickListener {  }
    }

    private fun validateParameter() {
        hideKeyboard()
    }

    private fun handleCode(code: String): String {
        val pattern = "oobCode=([^&]+)&".toRegex()

        val match = pattern.find(code)

        return match?.groupValues?.get(1) ?: ""
    }
}
