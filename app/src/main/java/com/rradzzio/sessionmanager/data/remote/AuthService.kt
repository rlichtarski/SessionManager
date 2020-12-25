package com.rradzzio.sessionmanager.data.remote

import com.rradzzio.sessionmanager.data.remote.requests.AuthLoginRequest
import com.rradzzio.sessionmanager.data.remote.responses.AuthTokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @GET("api/register")
    suspend fun register(
        @Query("email") email: String,
        @Query("password") password: String
    ): AuthTokenDto

    @POST("api/login")
    suspend fun login(
        @Body loginRequest: AuthLoginRequest
    ): Response<AuthTokenDto>

}