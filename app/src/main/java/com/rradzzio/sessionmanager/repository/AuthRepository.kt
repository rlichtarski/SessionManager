package com.rradzzio.sessionmanager.repository

import com.rradzzio.sessionmanager.data.remote.requests.AuthLoginRequest
import com.rradzzio.sessionmanager.data.remote.requests.AuthRegistrationRequest
import com.rradzzio.sessionmanager.domain.models.AuthToken
import com.rradzzio.sessionmanager.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(authLoginRequest: AuthLoginRequest) : Flow<Resource<AuthToken>>

    suspend fun register(authRegistrationRequest: AuthRegistrationRequest) : Flow<Resource<AuthToken>>

    suspend fun checkPreviousAuthUser() : Flow<Resource<AuthToken>>

}