package com.bed.seller.presentation.ui.common

import com.bed.seller.R

enum class Translate(val value: String) {
    DEFAULT("") {
        override fun toPortuguese() = R.string.generic_failure_message
    },
    REQUIRES_BEARER_TOKEN("This endpoint requires a Bearer token") {
        override fun toPortuguese() = R.string.generic_failure_requires_bearer_token
    },
    USER_ALREADY_REGISTERED("User already registered") {
        override fun toPortuguese() = R.string.generic_failure_message_email_already_registered
    },
    INVALID_LOGIN_CREDENTIALS("Invalid login credentials") {
        override fun toPortuguese() = R.string.generic_failure_invalid_credentials
    };

    abstract fun toPortuguese(): Int
}
