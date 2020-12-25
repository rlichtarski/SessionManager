package com.rradzzio.sessionmanager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthToken(
    val token: String? = null
) : Parcelable
