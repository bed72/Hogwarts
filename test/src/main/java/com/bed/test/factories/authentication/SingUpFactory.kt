package com.bed.test.factories.authentication

import arrow.core.left
import arrow.core.right

import com.bed.core.domain.models.failure.MessageModel
import com.bed.core.domain.models.authentication.SignUpModel

import com.bed.core.domain.parameters.authentication.SignUpParameters

class SingUpFactory {
    val params = SignUpParameters("Bed", "email@email.com", "P@Ssw0rd")

    val failureEntity = create(Mock.Failure)
    val successEntity = create(Mock.Success)

    fun create(mock: Mock) = when (mock) {
        Mock.Success -> SignUpModel("Bed", "email@email.com", "P@Ssw0rd").right()
        Mock.Failure -> MessageModel("Este e-mail já foi cadastrado!", "", "").left()
    }

    sealed class Mock {
        object Success : Mock()
        object Failure : Mock()
    }

}
