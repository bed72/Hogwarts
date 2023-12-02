package com.bed.seller.framework.network.clients

import javax.inject.Inject

import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.FirebaseAuth

interface FirebaseClient {
    val authentication: FirebaseAuth
}

class FirebaseClientImpl @Inject constructor() : FirebaseClient {
    override val authentication: FirebaseAuth get() = Firebase.auth
}
