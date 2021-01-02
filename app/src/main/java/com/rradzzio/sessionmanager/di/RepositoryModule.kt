package com.rradzzio.sessionmanager.di

import com.rradzzio.sessionmanager.data.local.AccountPropertiesDao
import com.rradzzio.sessionmanager.data.local.AuthTokenDao
import com.rradzzio.sessionmanager.data.local.model.AccountPropertiesEntityMapper
import com.rradzzio.sessionmanager.data.local.model.AuthTokenEntityMapper
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
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        authTokenDtoMapper: AuthTokenDtoMapper,
        authTokenEntityMapper: AuthTokenEntityMapper,
        accountPropertiesEntityMapper: AccountPropertiesEntityMapper
    ): AuthRepository {
        return AuthRepositoryImpl(
            authTokenRemoteSource = authTokenRemoteSource,
            authTokenDao = authTokenDao,
            accountPropertiesDao = accountPropertiesDao,
            authTokenDtoMapper = authTokenDtoMapper,
            authTokenEntityMapper = authTokenEntityMapper,
            accountPropertiesEntityMapper = accountPropertiesEntityMapper
        )
    }

}