package com.rradzzio.sessionmanager.repository

import com.rradzzio.sessionmanager.data.remote.AuthTokenRemoteSource
import com.rradzzio.sessionmanager.data.remote.requests.AuthLoginRequest
import com.rradzzio.sessionmanager.data.remote.requests.AuthRegistrationRequest
import com.rradzzio.sessionmanager.data.remote.responses.AuthTokenDtoMapper
import com.rradzzio.sessionmanager.domain.models.AuthToken
import com.rradzzio.sessionmanager.domain.models.ResponseType
import com.rradzzio.sessionmanager.domain.models.StateResponse
import com.rradzzio.sessionmanager.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authTokenRemoteSource: AuthTokenRemoteSource,
    private val authTokenDtoMapper: AuthTokenDtoMapper
) : AuthRepository {

    override suspend fun login(authLoginRequest: AuthLoginRequest): Flow<Resource<AuthToken>> {

        return authTokenRemoteSource.loginAuthToken(authLoginRequest)
            .map { response ->
                if(response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        Resource.success(
                            authTokenDtoMapper.mapToDomainModel(
                                it
                            )
                        )
                    }?: returnUnknownError()
                } else {
                    response.errorBody()?.let { responseBody ->
                        val errorMessage = JSONObject(responseBody.charStream().readText()).getString("error")
                        Resource.error(
                            errorMessage,
                            AuthToken(
                                errorResponse = StateResponse(
                                    message = errorMessage,
                                    errorResponseType = ResponseType.Dialog
                                )
                            )
                        )
                    }?: returnUnknownError()
                }
            }

    }

    override suspend fun register(authRegistrationRequest: AuthRegistrationRequest): Flow<AuthToken> {
        TODO("Not yet implemented")
    }

    private fun returnUnknownError(): Resource<AuthToken> {
        return Resource.error(
            "Unknown error",
            AuthToken(
                errorResponse = StateResponse(
                    "Unknown error",
                    errorResponseType = ResponseType.Toast
                )
            )
        )
    }

}