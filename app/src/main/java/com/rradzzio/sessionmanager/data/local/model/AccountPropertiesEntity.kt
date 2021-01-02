package com.rradzzio.sessionmanager.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_properties")
data class AccountPropertiesEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    var pk: Int?,

    @ColumnInfo(name = "email")
    var email: String?

)