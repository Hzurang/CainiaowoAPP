package com.example.login.net

import androidx.annotation.Keep

@Keep
data class LoginRequest(
    val mobi:String,
    val password:String
)