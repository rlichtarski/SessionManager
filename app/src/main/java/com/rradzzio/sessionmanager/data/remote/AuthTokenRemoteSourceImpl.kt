package com.rradzzio.sessionmanager.data.remote

import com.rradzzio.sessionmanager.data.remote.requests.AuthLoginRequest
import com.rradzzio.sessionmanager.data.remote.requests.AuthRegistrationRequest
import com.rradzzio.sessionmanager.data.remote.model.AuthTokenDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class AuthTokenRemoteSourceImpl @Inject constructor(
    private val authServiceApi: AuthService
) : AuthTokenRemoteSource {

    override suspend fun loginAuthToken(authLoginRequest: AuthLoginRequest): Flow<Response<AuthTokenDto>> =
        flow {
            val result = authServiceApi.login(authLoginRequest)
            Timber.d("loginAuthToken impl: ${result.code()}")
            Timber.d("loginAuthToken impl: ${result.message()}")
            Timber.d("loginAuthToken impl: ${result.body()}")
            Timber.d("loginAuthToken impl: ${result.raw()}")
            Timber.d("loginAuthToken impl: ${result.isSuccessful}")
            Timber.d("loginAuthToken impl: ${result.errorBody()}")
            emit(result)
        }

    override suspend fun registerAuthToken(authRegistrationRequest: AuthRegistrationRequest): Flow<Response<AuthTokenDto>> =
        flow {
            val result = authServiceApi.register(authRegistrationRequest)
            Timber.d("loginAuthToken impl: ${result.code()}")
            Timber.d("loginAuthToken impl: ${result.message()}")
            Timber.d("loginAuthToken impl: ${result.body()}")
            Timber.d("loginAuthToken impl: ${result.raw()}")
            Timber.d("loginAuthToken impl: ${result.isSuccessful}")
            Timber.d("loginAuthToken impl: ${result.errorBody()}")
            emit(result)
        }

}