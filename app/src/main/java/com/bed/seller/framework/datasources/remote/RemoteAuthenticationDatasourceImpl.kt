package com.bed.seller.framework.datasources.remote

import arrow.core.left
import arrow.core.right
import javax.inject.Inject

import kotlin.coroutines.resume
import kotlinx.coroutines.flow.flowOf
import kotlin.coroutines.suspendCoroutine

import com.google.firebase.auth.FirebaseUser

import com.bed.seller.alias.AuthenticationAppType

import com.bed.core.entities.input.ResetInput
import com.bed.core.entities.input.RecoverInput
import com.bed.core.entities.input.AuthenticationInput

import com.bed.seller.data.datasources.AuthenticationDatasource

import com.bed.seller.framework.network.clients.FirebaseClient
import com.bed.seller.framework.network.response.MessageResponse
import com.bed.seller.framework.network.response.AuthenticationResponse

class RemoteAuthenticationDatasourceImpl @Inject constructor(
    private val client: FirebaseClient
) : AuthenticationDatasource {

    init { client.authentication.setLanguageCode("pt-BR") }

    override fun isLoggedIn(): Boolean = client.authentication.currentUser != null

    override fun signOut(): Unit = client.authentication.signOut()

    override suspend fun reset(parameter: ResetInput): Boolean = suspendCoroutine { continuation ->
        client
            .authentication
            .confirmPasswordReset(parameter.code(), parameter.password())
            .addOnSuccessListener { continuation.resume(true) }
            .addOnFailureListener { continuation.resume(false) }
    }

    override suspend fun recover(parameter: RecoverInput): Boolean =
        suspendCoroutine { continuation ->
            client
                .authentication
                .sendPasswordResetEmail(parameter.email())
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }

    override suspend fun signUp(parameter: AuthenticationInput): AuthenticationAppType =
        suspendCoroutine { continuation ->
            client
                .authentication
                .createUserWithEmailAndPassword(parameter.email(), parameter.password())
                .addOnSuccessListener { data ->
                    data.user?.let { continuation.resume(buildSuccess(it)) }
                }
                .addOnFailureListener {
                    continuation.resume(buildFailure(it.localizedMessage))
                }
        }

    override suspend fun signIn(parameter: AuthenticationInput): AuthenticationAppType =
        suspendCoroutine { continuation ->
            client
                .authentication
                .signInWithEmailAndPassword(parameter.email(), parameter.password())
                .addOnSuccessListener { data ->
                    data.user?.let { continuation.resume(buildSuccess(it)) }
                }
                .addOnFailureListener {
                    continuation.resume(buildFailure(it.message))
                }
        }

    private fun buildSuccess(data: FirebaseUser) = flowOf(
        AuthenticationResponse(
            uid = data.uid,
            name = data.displayName,
            email = data.email,
            photo = data.photoUrl.toString(),
            emailVerified = data.isEmailVerified
        ).right()
    )

    private fun buildFailure(data: String? = null) = flowOf(MessageResponse(data).left())
}
