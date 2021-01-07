package com.rradzzio.sessionmanager.repository

import com.rradzzio.sessionmanager.data.local.AuthTokenDao
import com.rradzzio.sessionmanager.data.local.AutoAuthPrefsManager
import com.rradzzio.sessionmanager.data.local.model.AuthTokenEntity
import com.rradzzio.sessionmanager.data.local.model.AuthTokenEntityMapper
import com.rradzzio.sessionmanager.data.remote.AuthTokenRemoteSource
import com.rradzzio.sessionmanager.data.remote.model.AuthTokenDto
import com.rradzzio.sessionmanager.data.remote.model.AuthTokenDtoMapper
import com.rradzzio.sessionmanager.data.remote.requests.AuthLoginRequest
import com.rradzzio.sessionmanager.data.remote.requests.AuthRegistrationRequest
import com.rradzzio.sessionmanager.domain.models.AuthToken
import com.rradzzio.sessionmanager.domain.models.ResponseType
import com.rradzzio.sessionmanager.domain.models.StateResponse
import com.rradzzio.sessionmanager.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authTokenRemoteSource: AuthTokenRemoteSource,
    private val authTokenDao: AuthTokenDao,
    private val authTokenDtoMapper: AuthTokenDtoMapper,
    private val authTokenEntityMapper: AuthTokenEntityMapper,
    private val autoAuthPrefsManager: AutoAuthPrefsManager
) : AuthRepository {

    override suspend fun login(authLoginRequest: AuthLoginRequest): Flow<Resource<AuthToken>> {
        return authTokenRemoteSource.loginAuthToken(authLoginRequest)
            .map { response ->
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let { authTokenDto ->
                        val result = saveAuthToken(authTokenDto, authLoginRequest.email)
                        if (result < 0) {
                            Timber.e("Couldn't save an auth token into db.")
                        }
                        saveAuthenticatedUserToDataStorePrefs(authLoginRequest.email)
                        authTokenDto.let {
                            Resource.success(
                                authTokenDtoMapper.mapToDomainModel(it)
                            )
                        }
                    } ?: returnUnknownError()
                } else {
                    response.errorBody()?.let { responseBody ->
                        val errorMessage =
                            JSONObject(responseBody.charStream().readText()).getString("error")
                        Resource.error(
                            errorMessage,
                            AuthToken(
                                errorResponse = StateResponse(
                                    message = errorMessage,
                                    errorResponseType = ResponseType.Dialog
                                )
                            )
                        )
                    } ?: returnUnknownError()
                }
            }

    }

    override suspend fun register(authRegistrationRequest: AuthRegistrationRequest): Flow<Resource<AuthToken>> {
        return authTokenRemoteSource.registerAuthToken(authRegistrationRequest)
            .map { response ->
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let { authTokenDto ->
                        val result = saveAuthToken(authTokenDto, authRegistrationRequest.email)
                        if (result < 0) {
                            Timber.e("Couldn't save an auth token into db.")
                        }
                        saveAuthenticatedUserToDataStorePrefs(authRegistrationRequest.email)
                        authTokenDto.let {
                            Resource.success(
                                authTokenDtoMapper.mapToDomainModel(it)
                            )
                        }
                    } ?: returnUnknownError()
                } else {
                    response.errorBody()?.let { responseBody ->
                        val errorMessage =
                            JSONObject(responseBody.charStream().readText()).getString("error")
                        Resource.error(
                            errorMessage,
                            AuthToken(
                                errorResponse = StateResponse(
                                    message = errorMessage,
                                    errorResponseType = ResponseType.Dialog
                                )
                            )
                        )
                    } ?: returnUnknownError()
                }
            }
    }

    override suspend fun checkPreviousAuthUser(): Flow<Resource<AuthToken>> {

        return autoAuthPrefsManager.preferencesFlow
            .map { email ->

                if(email.isBlank()){
                    Timber.d("checkPreviousAuthUser: No previously authenticated user found.")
                    returnNoAuthTokenFound()
                }

                email.let {
                    authTokenDao.searchByEmail(email)?.let { authTokenEntity ->
                        authTokenEntity.account_pk?.let {
                            if (it > -1) {
                                authTokenDao.searchByPk(it)?.let { authToken ->
                                    if (authToken.token != null) {
                                        Resource.success(
                                            authTokenEntityMapper.mapToDomainModel(authToken)
                                        )
                                    } else returnNoAuthTokenFound()
                                }
                            } else returnNoAuthTokenFound()
                        }
                    }
                } ?: returnNoAuthTokenFound()

            }

    }

    private suspend fun saveAuthenticatedUserToDataStorePrefs(email: String) {
        autoAuthPrefsManager.saveAuthenticatedUserToDataStore(email)
        Timber.d("EMAIL IN DATA_STORE: ${autoAuthPrefsManager.readPreviousAuthUserEmail()}")
    }

    private suspend fun saveAuthToken(authTokenDto: AuthTokenDto, email: String): Long =
        authTokenDao.insert(
            authTokenEntity = AuthTokenEntity(
                token = authTokenDto.token,
                email = email
            )
        )

    private fun returnNoAuthTokenFound(): Resource<AuthToken> {
        return Resource.error(
            "No Auth Token found",
            AuthToken(
                errorResponse = StateResponse(
                    "No Auth Token found",
                    errorResponseType = ResponseType.None
                )
            )
        )
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