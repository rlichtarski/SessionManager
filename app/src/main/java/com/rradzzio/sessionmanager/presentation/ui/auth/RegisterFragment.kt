package com.rradzzio.sessionmanager.presentation.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.rradzzio.sessionmanager.R
import com.rradzzio.sessionmanager.databinding.FragmentRegisterBinding
import com.rradzzio.sessionmanager.domain.models.RegistrationCredentials
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseAuthFragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        binding.usernameRegister.addTextChangedListener(afterTextChangedListener)
        binding.passwordRegister.addTextChangedListener(afterTextChangedListener)
        binding.passwordConfirmRegister.addTextChangedListener(afterTextChangedListener)

        binding.registerButton.setOnClickListener{
            registerUser(
                binding.usernameRegister.text.toString(),
                binding.passwordRegister.text.toString(),
                binding.passwordConfirmRegister.text.toString()
            )
        }

        subscribeObservers()
    }

    private fun registerUser(email: String?, password: String?, confirmPassword: String?) {
        if(authViewModel.isValidForRegistration(email, password, confirmPassword)) {
            authViewModel.register(email!!, password!!)
        }
    }

    private fun subscribeObservers() {
        authViewModel.registrationCredentials.observe(viewLifecycleOwner, Observer { registrationCredentials ->
            registrationCredentials?.let {
                setRegistrationCredentialsFields(it)
            }
        })
    }

    private fun setRegistrationCredentialsFields(registrationCredentials: RegistrationCredentials) {
        binding.apply {
            usernameRegister.setText(registrationCredentials.email)
            passwordRegister.setText(registrationCredentials.password)
        }
    }

    private val afterTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val emailInput: String = binding.usernameRegister.text.toString().trim()
            val passwordInput: String = binding.passwordRegister.text.toString().trim()
            val confirmPasswordInput: String = binding.passwordConfirmRegister.text.toString().trim()

            binding.registerButton.isEnabled = (emailInput.isNotBlank()
                    && emailInput.isNotEmpty()
                    && passwordInput.isNotBlank()
                    && passwordInput.isNotEmpty()
                    && confirmPasswordInput.isNotBlank()
                    && confirmPasswordInput.isNotEmpty()
                    )
        }

        override fun afterTextChanged(s: Editable) {
            // ignore
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        authViewModel.setRegistrationFields(
            binding.usernameRegister.text.toString(),
            binding.passwordRegister.text.toString()
        )
    }
}