package com.bed.test.factories

import arrow.core.left
import arrow.core.right

import com.bed.core.values.Email
import com.bed.core.values.Code
import com.bed.core.values.Password

import com.bed.core.domain.models.failure.MessageModel
import com.bed.core.domain.models.authentication.AuthenticationModel

import com.bed.core.domain.parameters.authentication.ResetParameter
import com.bed.core.domain.parameters.authentication.RecoverParameter
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

class AuthenticationFactory {
    val resetValidParameter = ResetParameter(
        Code("5CQcsREkB5xcqbY1L..."),
        Password("P@ssw0rD")
    )

    val resetInvalidParameter = ResetParameter(
        Code(""),
        Password("")
    )

    val recoverValidParameter = RecoverParameter(
        Email("email@email.com")
    )

    val recoverInvalidParameter = RecoverParameter(
        Email("")
    )

    val signInAndSingUpValidParameter = AuthenticationParameter(
        Email("email@email.com"),
        Password("P@ssw0rD"),
    )

    val signInAndSingUpInvalidParameter = AuthenticationParameter(
        Email(""),
        Password(""),
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
