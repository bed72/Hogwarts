package com.bed.test.factories

import arrow.core.left
import arrow.core.right

import com.bed.core.values.EmailValue
import com.bed.core.values.CodeValue
import com.bed.core.values.PasswordValue

import com.bed.core.domain.models.failure.MessageModel
import com.bed.core.domain.models.authentication.AuthenticationModel

import com.bed.core.domain.parameters.authentication.ResetParameter
import com.bed.core.domain.parameters.authentication.RecoverParameter
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

class AuthenticationFactory {
    val resetValidParameter = ResetParameter(
        CodeValue("5CQcsREkB5xcqbY1L..."),
        PasswordValue("P@ssw0rD"),
        PasswordValue("P@ssw0rD"),
    )

    val resetInvalidParameter = ResetParameter(
        CodeValue(""),
        PasswordValue(""),
        PasswordValue(""),
    )

    val recoverValidParameter = RecoverParameter(
        EmailValue("email@email.com")
    )

    val recoverInvalidParameter = RecoverParameter(
        EmailValue("")
    )

    val signInAndSingUpValidParameter = AuthenticationParameter(
        EmailValue("email@email.com"),
        PasswordValue("P@ssw0rD"),
    )

    val signInAndSingUpInvalidParameter = AuthenticationParameter(
        EmailValue(""),
        PasswordValue(""),
    )

    val failure get() = create(Mock.Failure)
    val success get() = create(Mock.Success)

    private fun create(mock: Mock) = when (mock) {
        Mock.Failure ->
            MessageModel("Ops, um erro aconteceu.").left()
        Mock.Success -> AuthenticationModel(
            "5CQcsREkB5xcqbY1L...",
            "Gabriel Ramos",
            "bed@gmail.com",
            "https://github.com/bed72.png",
            false
        ).right()
    }

    sealed class Mock {
        data object Success : Mock()
        data object Failure : Mock()
    }
}
