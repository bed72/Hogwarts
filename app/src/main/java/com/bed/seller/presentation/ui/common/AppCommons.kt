package com.bed.seller.presentation.ui.common

class AppCommons : Commons {
    override fun mapper(message: String): Int =
        when (message) {
            Translate.USER_ALREADY_REGISTERED.value -> Translate.USER_ALREADY_REGISTERED.toPortuguese()
            Translate.INVALID_LOGIN_CREDENTIALS.value -> Translate.INVALID_LOGIN_CREDENTIALS.toPortuguese()
            else -> Translate.DEFAULT.toPortuguese()
        }
}
