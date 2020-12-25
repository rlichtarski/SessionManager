package com.rradzzio.sessionmanager.presentation.ui.auth

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.rradzzio.sessionmanager.data.remote.requests.AuthLoginRequest
import com.rradzzio.sessionmanager.domain.models.AuthToken
import com.rradzzio.sessionmanager.domain.models.LoginCredentials
import com.rradzzio.sessionmanager.domain.models.RegistrationCredentials
import com.rradzzio.sessionmanager.repository.AuthRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository
) : ViewModel(){

    private val _loginCredentials = MutableLiveData<LoginCredentials>()

    val loginCredentials: LiveData<LoginCredentials>
        get() = _loginCredentials

    private val _registrationCredentials = MutableLiveData<RegistrationCredentials>()

    val registrationCredentials: LiveData<RegistrationCredentials>
        get() = _registrationCredentials

    private val _loginResult = MutableLiveData<AuthToken>()

    val loginResult: LiveData<AuthToken>
        get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(
                AuthLoginRequest(
                    email = email,
                    password = password
                )
            ).collect {
                _loginResult.postValue(it)
            }
        }

    }

    fun setLoginFields(email: String?, password: String?) {
        _loginCredentials.value = LoginCredentials(email = email, password = password)
    }

    fun setRegistrationFields(email: String?, password: String?) {
        _registrationCredentials.value = RegistrationCredentials(email = email, password = password)
    }

    fun isValidForLogin(loginEmail: String?, loginPassword: String?): Boolean {
        if(loginEmail.isNullOrEmpty()
            || loginEmail.isNullOrBlank()
            || loginPassword.isNullOrEmpty()
            || loginPassword.isNullOrBlank()
            ) {
            return false
        }

        return loginPassword.length > 5
    }

}