package com.rradzzio.sessionmanager.presentation.ui.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

abstract class BaseAuthFragment constructor(
    layoutResource: Int
) : Fragment(layoutResource){

    val authViewModel: AuthViewModel by activityViewModels()

}