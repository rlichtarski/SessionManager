package com.rradzzio.sessionmanager.data.remote

import com.rradzzio.sessionmanager.data.remote.requests.AuthLoginRequest
import com.rradzzio.sessionmanager.data.remote.requests.AuthRegistrationRequest
import com.rradzzio.sessionmanager.data.remote.responses.AuthTokenDto
import com.rradzzio.sessionmanager.domain.models.LoginCredentials
import com.rradzzio.sessionmanager.domain.models.RegistrationCredentials
import kotlinx.coroutines.flow.Flow

interface AuthTokenRemoteSource {

    suspend fun loginAuthToken(authLoginRequest: AuthLoginRequest): Flow<AuthTokenDto>

    suspend fun registerAuthToken(authRegistrationRequest: AuthRegistrationRequest): Flow<AuthTokenDto>
}