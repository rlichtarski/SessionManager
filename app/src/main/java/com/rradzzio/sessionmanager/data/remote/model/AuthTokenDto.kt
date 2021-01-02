package com.rradzzio.sessionmanager.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthTokenDto(

    @Json(name = "token")
    val token: String? = null,

    @Json(name = "error")
    val error: String? = null
)
