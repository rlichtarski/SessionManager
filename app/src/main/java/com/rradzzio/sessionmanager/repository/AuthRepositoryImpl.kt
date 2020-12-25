package com.rradzzio.sessionmanager.repository

import com.rradzzio.sessionmanager.data.remote.AuthTokenRemoteSource
import com.rradzzio.sessionmanager.data.remote.requests.AuthLoginRequest
import com.rradzzio.sessionmanager.data.remote.requests.AuthRegistrationRequest
import com.rradzzio.sessionmanager.data.remote.responses.AuthTokenDtoMapper
import com.rradzzio.sessionmanager.domain.models.AuthToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authTokenRemoteSource: AuthTokenRemoteSource,
    private val authTokenDtoMapper: AuthTokenDtoMapper
) : AuthRepository {

    override suspend fun login(authLoginRequest: AuthLoginRequest): Flow<AuthToken> {
        return authTokenRemoteSource.loginAuthToken(authLoginRequest)
            .map {
                authTokenDtoMapper.mapToDomainModel(
                    it
                )
            }
    }

    override suspend fun register(authRegistrationRequest: AuthRegistrationRequest): Flow<AuthToken> {
        TODO("Not yet implemented")
    }


}