package com.bed.seller.datasources.remote

import arrow.core.left
import arrow.core.right
import javax.inject.Inject

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

import com.google.firebase.auth.FirebaseUser

import com.bed.core.data.datasources.remote.RemoteAuthenticationDatasource

import com.bed.core.domain.alias.AuthenticationType
import com.bed.core.domain.parameters.authentication.ResetParameter
import com.bed.core.domain.parameters.authentication.RecoverParameter
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

import com.bed.seller.framework.network.clients.FirebaseClient
import com.bed.seller.framework.network.response.message.toModel
import com.bed.seller.framework.network.response.authentication.toModel
import com.bed.seller.framework.network.response.message.MessageResponse
import com.bed.seller.framework.network.response.authentication.AuthenticationResponse

class RemoteAuthenticationDatasourceImpl @Inject constructor(
    private val client: FirebaseClient
) : RemoteAuthenticationDatasource {

    init { client.authentication.setLanguageCode("pt-BR") }

    override fun isLoggedIn(): Boolean = client.authentication.currentUser != null

    override fun signOut(): Unit = client.authentication.signOut()

    override suspend fun reset(parameter: ResetParameter): Boolean = suspendCoroutine { continuation ->
        client
            .authentication
            .confirmPasswordReset(parameter.code(), parameter.password())
            .addOnSuccessListener { continuation.resume(true) }
            .addOnFailureListener { continuation.resume(false) }
    }

    override suspend fun recover(parameter: RecoverParameter): Boolean =
        suspendCoroutine { continuation ->
            client
                .authentication
                .sendPasswordResetEmail(parameter.email())
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }

    override suspend fun signUp(parameter: AuthenticationParameter): AuthenticationType =
        suspendCoroutine { continuation ->
            client
                .authentication
                .createUserWithEmailAndPassword(parameter.email(), parameter.password())
                .addOnSuccessListener { data ->
                    data.user?.let { continuation.resume(buildSuccess(it).right()) }
                }
                .addOnFailureListener {
                    continuation.resume(buildFailure(it.localizedMessage).left())
                }
        }

    override suspend fun signIn(parameter: AuthenticationParameter): AuthenticationType =
        suspendCoroutine { continuation ->
            client
                .authentication
                .signInWithEmailAndPassword(parameter.email(), parameter.password())
                .addOnSuccessListener { data ->
                    data.user?.let { continuation.resume(buildSuccess(it).right()) }
                }
                .addOnFailureListener {
                    continuation.resume(buildFailure(it.message).left())
                }
        }

    private fun buildSuccess(data: FirebaseUser) = AuthenticationResponse(
        uid = data.uid,
        name = data.displayName,
        email = data.email,
        photo = data.photoUrl.toString(),
        emailVerified = data.isEmailVerified
    ).toModel()

    private fun buildFailure(data: String? = null) = MessageResponse(data).toModel()
}
