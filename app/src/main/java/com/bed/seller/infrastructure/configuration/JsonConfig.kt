package com.bed.seller.infrastructure.configuration

import kotlinx.serialization.json.Json

object SecurityJson {
    val Config = Json {
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
    }
}

object HttpJson {
    val config = Json {
        isLenient = true
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = false
    }
}


