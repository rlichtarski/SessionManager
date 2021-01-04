package com.rradzzio.sessionmanager.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rradzzio.sessionmanager.data.local.model.AuthTokenEntity

@Dao
interface AuthTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authTokenEntity: AuthTokenEntity): Long

    @Query("UPDATE auth_token SET token = null WHERE account_pk = :pk")
    fun nullifyToken(pk: Int): Int

    @Query("SELECT * FROM auth_token WHERE account_pk = :pk")
    suspend fun searchByPk(pk: Int): AuthTokenEntity?

    @Query("SELECT * FROM auth_token WHERE email = :email")
    suspend fun searchByEmail(email: String): AuthTokenEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(authTokenEntity: AuthTokenEntity): Long

}