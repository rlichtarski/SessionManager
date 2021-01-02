package com.rradzzio.sessionmanager.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "auth_token",
    foreignKeys = [
        ForeignKey(
            entity = AccountPropertiesEntity::class,
            parentColumns = ["pk"],
            childColumns = ["account_pk"],
            onDelete = CASCADE
        )
    ])
data class AuthTokenEntity(

    @PrimaryKey
    @ColumnInfo(name = "account_pk")
    var account_pk: Int? = -1,

    @ColumnInfo(name = "token")
    var authToken: String? = null

)