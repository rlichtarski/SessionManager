package com.rradzzio.sessionmanager.presentation.ui.auth

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.rradzzio.sessionmanager.domain.models.LoginCredentials
import com.rradzzio.sessionmanager.domain.models.RegistrationCredentials

class AuthViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _loginCredentials = MutableLiveData<LoginCredentials>()

    val loginCredentials: LiveData<LoginCredentials>
        get() = _loginCredentials

    private val _registrationCredentials = MutableLiveData<RegistrationCredentials>()

    val registrationCredentials: LiveData<RegistrationCredentials>
        get() = _registrationCredentials

    fun setLoginFields(email: String?, password: String?) {
            _loginCredentials.value = LoginCredentials(email = email, password = password)
    }

    fun setRegistrationFields(email: String?, password: String?) {
        _registrationCredentials.value = RegistrationCredentials(email = email, password = password)
    }

}