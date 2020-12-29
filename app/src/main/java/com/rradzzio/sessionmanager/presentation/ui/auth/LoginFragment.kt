package com.rradzzio.sessionmanager.presentation.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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

        binding.usernameLogin.addTextChangedListener(afterTextChangedListener)
        binding.passwordLogin.addTextChangedListener(afterTextChangedListener)

        binding.loginButton.setOnClickListener{
            loginUser(
                binding.usernameLogin.text.toString(),
                binding.passwordLogin.text.toString()
            )
        }

        subscribeObservers()
    }

    private fun loginUser(email: String?, password: String?) {
        if(authViewModel.isValidForLogin(email, password)) {
            authViewModel.login(email!!, password!!)
        }
    }

    private fun subscribeObservers() {
        authViewModel.loginCredentials.observe(viewLifecycleOwner, { loginCredentials ->
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

    private val afterTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val emailInput: String = binding.usernameLogin.text.toString().trim()
            val passwordInput: String = binding.passwordLogin.text.toString().trim()

            binding.loginButton.isEnabled = (emailInput.isNotBlank()
                    && emailInput.isNotEmpty()
                    && passwordInput.isNotBlank()
                    && passwordInput.isNotEmpty())
        }

        override fun afterTextChanged(s: Editable) {
            // ignore
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