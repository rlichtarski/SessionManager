package com.rradzzio.sessionmanager.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.rradzzio.sessionmanager.R
import com.rradzzio.sessionmanager.databinding.FragmentLoginBinding
import com.rradzzio.sessionmanager.domain.models.LoginCredentials
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseAuthFragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        authViewModel.loginCredentials.observe(viewLifecycleOwner, Observer { loginCredentials ->
            loginCredentials?.let {
                setLoginCredentialsFields(it)
            }
        })
    }

    private fun setLoginCredentialsFields(loginCredentials: LoginCredentials) {
        binding.apply {
            usernameLogin.setText(loginCredentials.email)
            passwordLogin.setText(loginCredentials.password)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        authViewModel.setLoginFields(
            binding.usernameLogin.text.toString(),
            binding.passwordLogin.text.toString()
        )
    }
}