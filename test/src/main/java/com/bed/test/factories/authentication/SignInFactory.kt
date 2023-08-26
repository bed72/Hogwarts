package com.bed.test.factories.authentication

import arrow.core.left
import arrow.core.right

import com.bed.core.values.EmailValue
import com.bed.core.values.PasswordValue

import com.bed.core.domain.parameters.authentication.SignInParameter

import com.bed.core.domain.models.failure.MessageModel
import com.bed.core.domain.models.authentication.AuthenticationModel
import com.bed.core.domain.models.authentication.AuthenticationUserModel
import com.bed.core.domain.models.authentication.AuthenticationMetadataModel

class SignInFactory {
    val signInParameter = SignInParameter(
        EmailValue("email@email.com"),
        PasswordValue("P@ssw0rD"),
    )

    val invalidParameter = SignInParameter(
        EmailValue(""),
        PasswordValue(""),
    )

    val failure get() = create(Mock.Failure)
    val success get() = create(Mock.Success)

    private fun create(mock: Mock) = when (mock) {
        Mock.Failure ->
            MessageModel("Credenciais inválidas.").left()
        Mock.Success -> AuthenticationModel(
            3600,
            "5CQcsREkB5xcqbY1L...",
            "5CQcsREkB5xcqbY1L...",
            AuthenticationUserModel("bed@email.com", AuthenticationMetadataModel("Bed"))
        ).right()
    }

    sealed class Mock {
        data object Success : Mock()
        data object Failure : Mock()
    }
}
