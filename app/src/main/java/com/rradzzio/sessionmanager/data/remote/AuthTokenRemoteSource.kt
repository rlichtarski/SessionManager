package com.rradzzio.sessionmanager.data.remote

import com.rradzzio.sessionmanager.data.remote.requests.AuthLoginRequest
import com.rradzzio.sessionmanager.data.remote.requests.AuthRegistrationRequest
import com.rradzzio.sessionmanager.data.remote.model.AuthTokenDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthTokenRemoteSource {

    suspend fun loginAuthToken(authLoginRequest: AuthLoginRequest): Flow<Response<AuthTokenDto>>

    suspend fun registerAuthToken(authRegistrationRequest: AuthRegistrationRequest): Flow<Response<AuthTokenDto>>
}