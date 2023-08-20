package com.bed.seller.framework.constants

import com.bed.seller.BuildConfig

enum class StorageConstant(val value: String) {
    DATASTORE_ACCESS_TOKEN("${BuildConfig.APPLICATION_ID}_access_token"),
    DATASTORE_REFRESH_TOKEN("${BuildConfig.APPLICATION_ID}_refresh_token"),

}
