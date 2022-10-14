package com.bed.seller.data.usecases.mocks

import com.bed.seller.domain.usecases.auth.AuthUseCase

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.AuthBodyRequestEntity

class CommonMock {
    companion object {
        private const val PARAMS_NAME = "Bed"
        const val PARAMS_EMAIL = "email@email.com"
        const val PARAMS_PASSWORD = "C!c@d@330172L"
        val PARAMS_SIGN_UP_REQUEST = AuthBodyRequestEntity(PARAMS_NAME, PARAMS_EMAIL, PARAMS_PASSWORD)
    }
}