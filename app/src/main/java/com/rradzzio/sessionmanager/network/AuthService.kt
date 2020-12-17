package com.rradzzio.sessionmanager.network

import retrofit2.http.GET
import retrofit2.http.Query

interface AuthService {

    @GET("api/register")
    suspend fun register(
        @Query("email") email: String,
        @Query("password") password: String
    )

    @GET("api/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    )

}