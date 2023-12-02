package com.bed.seller.presentation.commons.states

import arrow.core.Nel

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import com.bed.core.values.NameValue
import com.bed.core.values.EmailValue
import com.bed.core.values.MessageValue
import com.bed.core.values.PasswordValue
import com.bed.core.values.getFirstMessage

class FormState {

    private val _state = MutableStateFlow<States<String>>(States.Loading)
    val state: StateFlow<States<String>> get() = _state.asStateFlow()

    fun set(value: String, type: Type) {
        _state.update { States.Loading }

        when (type) {
            Type.Name -> name(value)
            Type.Email -> email(value)
            Type.Password -> password(value)
        }
    }

    private fun name(value: String) {
        NameValue(value).fold(::setFailure) { setSuccess(it()) }
    }

    private fun email(value: String) {
        EmailValue(value).fold(::setFailure) { setSuccess(it()) }
    }

    private fun password(value: String) {
        PasswordValue(value).fold(::setFailure) { setSuccess(it()) }
    }

    private fun setFailure(failure: Nel<MessageValue>) {
        _state.update { States.Failure(failure.getFirstMessage()) }
    }

    private fun  setSuccess(success: String) {
        _state.update { States.Success(success) }
    }

    sealed class Type {
        data object Name : Type()
        data object Email : Type()
        data object Password : Type()
    }
}
