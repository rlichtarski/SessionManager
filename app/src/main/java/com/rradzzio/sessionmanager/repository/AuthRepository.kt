package com.rradzzio.sessionmanager.repository

import com.rradzzio.sessionmanager.data.remote.requests.AuthLoginRequest
import com.rradzzio.sessionmanager.data.remote.requests.AuthRegistrationRequest
import com.rradzzio.sessionmanager.domain.models.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(authLoginRequest: AuthLoginRequest) : Flow<AuthToken>

    suspend fun register(authRegistrationRequest: AuthRegistrationRequest) : Flow<AuthToken>

}