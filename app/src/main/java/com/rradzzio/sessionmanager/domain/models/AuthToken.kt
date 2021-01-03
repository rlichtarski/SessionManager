package com.rradzzio.sessionmanager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthToken(
    val pk: Int? = null,
    val token: String? = null,
    val error: String? = null,
    val errorResponse: StateResponse? = null
): Parcelable

@Parcelize
data class StateResponse(
    val message: String?,
    val errorResponseType: ResponseType
): Parcelable

sealed class ResponseType : Parcelable {

    @Parcelize
    object Toast : ResponseType()

    @Parcelize
    object Dialog : ResponseType()

    @Parcelize
    object None : ResponseType()

}