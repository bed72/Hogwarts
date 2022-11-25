package com.bed.seller.data.usecases.auth.mocks

import arrow.core.left
import arrow.core.right

import com.bed.seller.R

import com.bed.seller.data.usecases.mocks.CommonMock

import com.bed.seller.domain.entities.ResponseEntity
import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.usecases.auth.SignUpUseCase

import com.bed.seller.infrastructure.network.models.ResponseModel
import com.bed.seller.infrastructure.network.models.auth.toEntity
import com.bed.seller.infrastructure.network.models.auth.AuthResponseModel
import com.bed.seller.infrastructure.network.models.auth.user.UserMetaDataResponseModel
import com.bed.seller.infrastructure.network.models.auth.user.UserResponseModel

import com.bed.seller.infrastructure.network.models.failure.toEntity
import com.bed.seller.infrastructure.network.models.failure.MessageFailureResponseModel

class AuthMock {
    private val userMetadata = UserMetaDataResponseModel(
        name = "Mock_Name"
    )

    private val user = UserResponseModel(
        id = "Mock_User_Id",
        email = "Mock_User_Email",
        userMetadata = userMetadata
    )

    val authFailureModel = MessageFailureResponseModel(
            message = "User already registered"
        )

    val authSuccessModel = AuthResponseModel(
            accessToken = "Mock_Access_Token",
            refreshToken = "Mock_Refresh_Token",
            user = user
        )

    val pathSignUp = PathEntity.SIGN_UP
    val paramsSignUp = SignUpUseCase.Params(pathSignUp, CommonMock.PARAMS_SIGN_UP_REQUEST)

    val failureModel = makeModel(ResponseModel.FailureModel)
    val successModel = makeModel(ResponseModel.SuccessModel)

    val failureEntity = makeEntity(ResponseEntity.FailureEntity)
    val successEntity = makeEntity(ResponseEntity.SuccessEntity)

    val intIdFailureBedRequest = 2131951670 // Represents the id of the error string
    val stringIdFailureBedRequest = R.string.generic_failure_message_email_already_registered
    // <string name="generic_failure_message_email_already_registered">E-mail já cadastrado!</string>

    private fun makeModel(response: ResponseModel) =
        when (response) {
            ResponseModel.FailureModel -> ResponseModel(400, authFailureModel).left()
            ResponseModel.SuccessModel -> ResponseModel(200, authSuccessModel).right()
        }

    private fun makeEntity(response: ResponseEntity) =
        when (response) {
            ResponseEntity.FailureEntity -> ResponseEntity(400, authFailureModel.toEntity()).left()
            ResponseEntity.SuccessEntity -> ResponseEntity(200, authSuccessModel.toEntity()).right()
        }

    sealed class ResponseModel {
        object SuccessModel : ResponseModel()
        object FailureModel : ResponseModel()
    }

    sealed class ResponseEntity {
        object SuccessEntity : ResponseEntity()
        object FailureEntity : ResponseEntity()
    }
}
