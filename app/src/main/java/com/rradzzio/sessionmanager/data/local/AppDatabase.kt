package com.rradzzio.sessionmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rradzzio.sessionmanager.data.local.model.AccountPropertiesEntity
import com.rradzzio.sessionmanager.data.local.model.AuthTokenEntity

@Database(entities = [AuthTokenEntity::class, AccountPropertiesEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun getAccountPropertiesDao(): AccountPropertiesDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }

}