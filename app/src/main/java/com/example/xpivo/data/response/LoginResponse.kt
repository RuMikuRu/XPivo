package com.example.xpivo.data.response

import kotlinx.serialization.SerialName

data class LoginResponse(
    @SerialName("token")
    val userToken: String
)
