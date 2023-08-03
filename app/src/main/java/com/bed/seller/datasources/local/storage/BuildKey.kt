package com.bed.seller.datasources.local.storage

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

fun buildKey(value: String): Preferences.Key<String> = stringPreferencesKey(value)
