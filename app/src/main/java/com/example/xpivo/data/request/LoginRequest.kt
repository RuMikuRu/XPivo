package com.example.xpivo.data.request

import kotlinx.serialization.SerialName


data class LoginRequest(
    @SerialName("Email")
    val email:String,
    @SerialName("Password")
    val password:String
)
