package com.bed.test.factories.authentication

import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.flow.flowOf

import com.bed.core.entities.output.MessageOutput
import com.bed.core.entities.output.AuthenticationOutput

import com.bed.core.entities.input.ResetInput
import com.bed.core.entities.input.RecoverInput
import com.bed.core.entities.input.AuthenticationInput

class AuthenticationFactory {
    val resetInvalidParameter = ResetInput("", "").leftOrNull()!!
    val resetValidParameter =
        ResetInput("5CQcsREkB5xcqbY1L...", "P@ssw0rD").getOrNull()!!

    val recoverInvalidParameter = RecoverInput("").leftOrNull()!!
    val recoverValidParameter = RecoverInput("email@email.com").getOrNull()!!

    val signInAndSingUpInvalidParameter =
        AuthenticationInput("", "").leftOrNull()!!
    val signInAndSingUpValidParameter =
        AuthenticationInput("email@email.com", "P@ssw0rD").getOrNull()!!

    val failure get() = flowOf(create(Mock.Failure))
    val success get() = flowOf(create(Mock.Success))

    private fun create(mock: Mock) = when (mock) {
        Mock.Failure ->
            MessageOutput("Ops, um erro aconteceu.").left()
        Mock.Success -> AuthenticationOutput(
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
