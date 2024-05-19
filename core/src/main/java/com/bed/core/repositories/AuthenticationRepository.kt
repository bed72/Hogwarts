package com.bed.core.repositories

import kotlinx.coroutines.flow.Flow

import com.bed.core.alias.AuthenticationCoreType

import com.bed.core.entities.input.ResetInput
import com.bed.core.entities.input.RecoverInput
import com.bed.core.entities.input.AuthenticationInput

interface AuthenticationRepository {
    fun signOut()
    fun isLoggedIn(): Boolean
    suspend fun reset(parameter: ResetInput): Flow<Boolean>
    suspend fun recover(parameter: RecoverInput): Flow<Boolean>
    suspend fun signUp(parameter: AuthenticationInput): AuthenticationCoreType
    suspend fun signIn(parameter: AuthenticationInput): AuthenticationCoreType
}
