package com.rradzzio.sessionmanager.data.remote.model

import com.rradzzio.sessionmanager.domain.models.AuthToken
import com.rradzzio.sessionmanager.domain.util.DomainMapper

class AuthTokenDtoMapper : DomainMapper<AuthTokenDto, AuthToken> {

    override fun mapToDomainModel(model: AuthTokenDto): AuthToken {
        return AuthToken(
            token = model.token,
            error = model.error
        )
    }

    override fun mapFromDomainModel(domainModel: AuthToken): AuthTokenDto {
        return AuthTokenDto(
            token = domainModel.token,
            error = domainModel.error
        )
    }

}