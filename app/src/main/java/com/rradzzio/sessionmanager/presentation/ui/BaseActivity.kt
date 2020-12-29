package com.rradzzio.sessionmanager.presentation.ui

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun displayProgressBar(loading: Boolean)

}