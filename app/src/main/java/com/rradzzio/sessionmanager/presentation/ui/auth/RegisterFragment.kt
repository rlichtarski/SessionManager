package com.rradzzio.sessionmanager.presentation.ui.auth

import android.os.Bundle
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

        subscribeObservers()
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

    override fun onDestroyView() {
        super.onDestroyView()
        authViewModel.setRegistrationFields(
            binding.usernameRegister.text.toString(),
            binding.passwordRegister.text.toString()
        )
    }
}