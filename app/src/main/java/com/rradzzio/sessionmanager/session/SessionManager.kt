package com.rradzzio.sessionmanager.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rradzzio.sessionmanager.data.local.AuthTokenDao
import com.rradzzio.sessionmanager.domain.models.AuthToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val authTokenDao: AuthTokenDao
) {

    private val _cachedToken = MutableLiveData<AuthToken?>()

    val cachedToken: LiveData<AuthToken?>
        get() = _cachedToken

    fun login(newValue: AuthToken) {
        Timber.d("login: setting new value")
        setValue(newValue)
    }

    fun logout() {
        Timber.d("logout: nullifying the token")

        CoroutineScope(Dispatchers.IO).launch {
            var errorMessage: String? = null
            try {
                _cachedToken.value!!.pk?.let {
                    authTokenDao.nullifyToken(it)
                }?: throw CancellationException("Token Error. Logging out user.")
            } catch (e: CancellationException) {
                Timber.e("logout: ${e.message}")
                errorMessage = e.message
            } catch (e: Exception) {
                Timber.e("logout: ${e.message}")
                errorMessage = errorMessage + "\n" + e.message
            } finally {
                errorMessage?.let {
                    Timber.e("logout: $errorMessage")
                }
                Timber.d("logout: finally")
                setValue(null)
            }

        }

    }

    private fun setValue(newValue: AuthToken?) {
        CoroutineScope(Dispatchers.Main).launch {
            if (_cachedToken.value != newValue) {
                _cachedToken.value = newValue
            }
        }

    }

}