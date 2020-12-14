package com.rradzzio.sessionmanager.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rradzzio.sessionmanager.R
import com.rradzzio.sessionmanager.databinding.FragmentLauncherBinding

class LauncherFragment : Fragment(R.layout.fragment_launcher) {

    private lateinit var binding: FragmentLauncherBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLauncherBinding.bind(view)

        binding.loginTxt.setOnClickListener {
            navLogin()
        }

        binding.registerTxt.setOnClickListener {
            navRegister()
        }

        binding.focusableView.requestFocus()


    }

    private fun navRegister() {
        findNavController().navigate(R.id.action_launcherFragment_to_registerFragment)
    }

    private fun navLogin() {
        findNavController().navigate(R.id.action_launcherFragment_to_loginFragment)
    }

}