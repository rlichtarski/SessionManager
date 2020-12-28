package com.rradzzio.sessionmanager.presentation.ui

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(),
    UIStateListener{

    override fun onLoadingChange(loading: Boolean) {
        displayProgressBar(loading)
    }

    abstract fun displayProgressBar(loading: Boolean)

}