package com.bed.test.factories.authentication

import arrow.core.left
import arrow.core.right

import com.bed.core.domain.models.failure.MessageModel
import com.bed.core.domain.models.authentication.AuthenticationModel

import com.bed.core.domain.parameters.authentication.ResetParameter
import com.bed.core.domain.parameters.authentication.RecoverParameter
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

class AuthenticationFactory {
    val resetInvalidParameter = ResetParameter("", "").leftOrNull()!!
    val resetValidParameter =
        ResetParameter("5CQcsREkB5xcqbY1L...", "P@ssw0rD").getOrNull()!!

    val recoverInvalidParameter = RecoverParameter("").leftOrNull()!!
    val recoverValidParameter = RecoverParameter("email@email.com").getOrNull()!!

    val signInAndSingUpInvalidParameter =
        AuthenticationParameter("", "").leftOrNull()!!
    val signInAndSingUpValidParameter =
        AuthenticationParameter("email@email.com", "P@ssw0rD").getOrNull()!!

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
