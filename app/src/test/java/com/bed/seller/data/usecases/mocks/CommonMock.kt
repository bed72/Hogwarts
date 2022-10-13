package com.bed.seller.data.usecases.mocks

import com.bed.seller.domain.entities.auth.AuthRequestEntity

class CommonMock {
    companion object {
        private const val PARAMS_NAME = "Bed"
        const val PARAMS_EMAIL = "email@email.com"
        const val PARAMS_PASSWORD = "C!c@d@330172L"
        val PARAMS_SIGN_UP_REQUEST =  AuthRequestEntity(PARAMS_NAME, PARAMS_EMAIL, PARAMS_PASSWORD)
    }
}