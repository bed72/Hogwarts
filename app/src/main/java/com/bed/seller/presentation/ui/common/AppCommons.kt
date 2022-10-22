package com.bed.seller.presentation.ui.common

import com.bed.seller.R

class AppCommons : Commons {
    override fun mapper(status: Int): Int =
        when (status) {
            400 -> R.string.generic_failure_message_email_already_registered
            else -> R.string.generic_failure_message
        }
}
