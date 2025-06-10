package com.example.xpivo.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class LoginResponse(
    @SerializedName("id")
    val userId: Int,
    @SerializedName("token")
    val userToken: String
)
