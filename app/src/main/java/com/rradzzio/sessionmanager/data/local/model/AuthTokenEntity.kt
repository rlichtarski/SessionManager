package com.rradzzio.sessionmanager.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "auth_token")
data class AuthTokenEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_pk")
    var account_pk: Int? = 0,

    @ColumnInfo(name = "token")
    var token: String? = null,

    @ColumnInfo(name = "email")
    var email: String? = null

)