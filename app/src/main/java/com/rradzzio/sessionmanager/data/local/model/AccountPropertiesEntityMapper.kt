package com.rradzzio.sessionmanager.data.local.model

import com.rradzzio.sessionmanager.domain.models.AccountProperties
import com.rradzzio.sessionmanager.domain.util.DomainMapper

class AccountPropertiesEntityMapper : DomainMapper<AccountPropertiesEntity, AccountProperties> {

    override fun mapToDomainModel(model: AccountPropertiesEntity): AccountProperties {
        return AccountProperties(
            pk = model.pk,
            email = model.email
        )
    }

    override fun mapFromDomainModel(domainModel: AccountProperties): AccountPropertiesEntity {
        return AccountPropertiesEntity(
            pk = domainModel.pk,
            email = domainModel.email
        )
    }
}