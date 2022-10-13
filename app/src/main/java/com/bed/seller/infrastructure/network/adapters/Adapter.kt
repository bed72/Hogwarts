package com.bed.seller.infrastructure.network.adapters

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import io.ktor.http.HttpStatusCode

import io.ktor.client.call.body
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.request.HttpRequestBuilder

import com.bed.seller.infrastructure.network.models.responses.ResponseModel

/**
 * Base Client
suspend inline fun <reified F, reified S> HttpClient.safe(
    block: HttpRequestBuilder.() -> Unit,
): Either<F, S> {
    val response = request { block() }

    return when (response.status) {
        HttpStatusCode.BadRequest -> response.body<F>().left()
        HttpStatusCode.UnprocessableEntity -> response.body<F>().left()
        HttpStatusCode.Created, HttpStatusCode.OK -> response.body<S>().right()
        else -> response.body<F>().left()
    }
}
*/

suspend inline fun <reified F, reified S> HttpClient.safe(
    block: HttpRequestBuilder.() -> Unit,
): Either<ResponseModel<F>, ResponseModel<S>> {
    val response = request { block() }

    return when (val status = response.status) {
        HttpStatusCode.BadRequest -> buildResponseFailure(status.value, response)
        HttpStatusCode.UnprocessableEntity -> buildResponseFailure(status.value, response)
        else -> buildResponseSuccess(status.value, response)
    }
}

suspend inline fun <reified F> buildResponseFailure(status: Int, response: HttpResponse) =
    ResponseModel(status = status, data = response.body<F>()).left()

suspend inline fun <reified S> buildResponseSuccess(status: Int, response: HttpResponse) =
    ResponseModel(status = status, data = response.body<S>()).right()

