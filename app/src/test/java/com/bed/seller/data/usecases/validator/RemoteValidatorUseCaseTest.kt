package com.bed.seller.data.usecases.validator

import com.nhaarman.mockitokotlin2.whenever

import kotlin.test.assertEquals
import kotlin.test.assertNotNull

import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import com.bed.seller.domain.entities.form.TextFieldEntity
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.data.client.ValidatorClient
import com.bed.seller.data.usecases.mocks.CommonMock
import com.bed.seller.data.usecases.validator.mocks.ValidatorFactoryMock

@RunWith(MockitoJUnitRunner::class)
class RemoteValidatorUseCaseTest {

    @Mock
    private lateinit var validatorClient: ValidatorClient

    private lateinit var validatorUseCase: ValidatorUseCase

    private val validatorFactoryMock = ValidatorFactoryMock()

    @Before
    fun setUp() {
        validatorUseCase = RemoteValidatorUseCase(validatorClient)
    }

    @Test
    fun `Should call Validator Client with Validate Name to input Name`() {
        whenever(validatorClient.validateName(ValidatorFactoryMock.PARAMS_NAME))
            .thenReturn(validatorFactoryMock.nameIsValid)

        val response = validatorUseCase.validateName(ValidatorFactoryMock.PARAMS_NAME)

        assertNotNull(response)
        assertEquals(response.valid, true)
        assertEquals(response.textField, TextFieldEntity.NAME)
        assertEquals(response.value, ValidatorFactoryMock.PARAMS_NAME)
    }

    @Test
    fun `Should call Validator Client with Validate Email to input Email`() {
        whenever(validatorClient.validateEmail(CommonMock.PARAMS_EMAIL))
            .thenReturn(validatorFactoryMock.emailIsValid)

        val response = validatorUseCase.validateEmail(CommonMock.PARAMS_EMAIL)

        assertNotNull(response)
        assertEquals(response.valid, true)
        assertEquals(response.textField, TextFieldEntity.EMAIL)
        assertEquals(response.value, CommonMock.PARAMS_EMAIL)
    }
    @Test
    fun `Should call Validator Client with Validate Password to input Password`() {
        whenever(validatorClient.validatePassword(CommonMock.PARAMS_PASSWORD))
            .thenReturn(validatorFactoryMock.passwordIsValid)

        val response = validatorUseCase.validatePassword(CommonMock.PARAMS_PASSWORD)

        assertNotNull(response)
        assertEquals(response.valid, true)
        assertEquals(response.textField, TextFieldEntity.PASSWORD)
        assertEquals(response.value, CommonMock.PARAMS_PASSWORD)
    }
}