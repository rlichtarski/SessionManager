package com.rradzzio.sessionmanager.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rradzzio.sessionmanager.data.local.model.AccountPropertiesEntity

@Dao
interface AccountPropertiesDao {

    @Query("SELECT * FROM account_properties WHERE email = :email")
    suspend fun searchByEmail(email: String): AccountPropertiesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(accountPropertiesEntity: AccountPropertiesEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(accountPropertiesEntity: AccountPropertiesEntity): Long

}