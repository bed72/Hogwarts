package com.bed.seller.infrastructure.network.models.requests

abstract class AuthBodyRequestModel {
    abstract val email: String
    abstract val password: String
}
