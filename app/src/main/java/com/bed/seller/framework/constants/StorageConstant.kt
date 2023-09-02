package com.bed.seller.framework.constants

import com.bed.seller.BuildConfig

enum class StorageConstant(val value: String) {
    DATASTORE_UID_USER("${BuildConfig.APPLICATION_ID}_uid_user"),
    DATASTORE_EMAIL_USER("${BuildConfig.APPLICATION_ID}_email_user"),
}
