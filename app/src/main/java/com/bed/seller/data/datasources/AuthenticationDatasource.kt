package com.bed.seller.data.datasources

import com.bed.seller.alias.AuthenticationAppType

import com.bed.core.entities.input.ResetInput
import com.bed.core.entities.input.RecoverInput
import com.bed.core.entities.input.AuthenticationInput

interface AuthenticationDatasource {
    fun signOut()
    fun isLoggedIn(): Boolean
    suspend fun reset(parameter: ResetInput): Boolean
    suspend fun recover(parameter: RecoverInput): Boolean
    suspend fun signUp(parameter: AuthenticationInput): AuthenticationAppType
    suspend fun signIn(parameter: AuthenticationInput): AuthenticationAppType
}
