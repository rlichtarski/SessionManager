package com.rradzzio.sessionmanager.di

import com.rradzzio.sessionmanager.data.remote.AuthTokenRemoteSource
import com.rradzzio.sessionmanager.data.remote.model.AuthTokenDtoMapper
import com.rradzzio.sessionmanager.repository.AuthRepository
import com.rradzzio.sessionmanager.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        authTokenRemoteSource: AuthTokenRemoteSource,
        authTokenDtoMapper: AuthTokenDtoMapper
    ): AuthRepository {
        return AuthRepositoryImpl(
            authTokenRemoteSource = authTokenRemoteSource,
            authTokenDtoMapper = authTokenDtoMapper
        )
    }

}