package com.rradzzio.sessionmanager.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.rradzzio.sessionmanager.data.local.AutoAuthPrefsManager.PreferencesKeys.PREVIOUS_AUTH_USER
import com.rradzzio.sessionmanager.util.Constants.Companion.AUTO_AUTH_PREFS
import com.rradzzio.sessionmanager.util.Constants.Companion.PREVIOUS_AUTH_USER_PREF
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

class AutoAuthPrefsManager @Inject constructor(context: Context){

    private val dataStore = context.createDataStore(name = AUTO_AUTH_PREFS)

    suspend fun readPreviousAuthUserEmail(): String? {
        val preferences = dataStore.data.first()
        return preferences[PREVIOUS_AUTH_USER]
    }

    suspend fun saveAuthenticatedUserToDataStore(email: String) {
        dataStore.edit { _dataStore ->
            _dataStore[PREVIOUS_AUTH_USER] = email
        }
    }

    private object PreferencesKeys {
        val PREVIOUS_AUTH_USER = preferencesKey<String>(PREVIOUS_AUTH_USER_PREF)
    }

}