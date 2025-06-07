package com.example.xpivo.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class LoginResponse(
    @SerializedName("token")
    val userToken: String
)
