package com.rradzzio.sessionmanager.data.local.model

import com.rradzzio.sessionmanager.domain.models.AuthToken
import com.rradzzio.sessionmanager.domain.util.DomainMapper

class AuthTokenEntityMapper : DomainMapper<AuthTokenEntity, AuthToken> {

    override fun mapToDomainModel(model: AuthTokenEntity): AuthToken {
        return AuthToken(
            token = model.authToken
        )
    }

    override fun mapFromDomainModel(domainModel: AuthToken): AuthTokenEntity {
        return AuthTokenEntity(
            authToken = domainModel.token
        )
    }
}