package com.rradzzio.sessionmanager.presentation.ui.auth

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.rradzzio.sessionmanager.domain.models.LoginCredentials
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _loginCredentials = MutableLiveData<LoginCredentials>()

    val loginCredentials: LiveData<LoginCredentials>
        get() = _loginCredentials

    fun setLoginFields(email: String?, password: String?) {
            _loginCredentials.value = LoginCredentials(email = email, password = password)
    }

}