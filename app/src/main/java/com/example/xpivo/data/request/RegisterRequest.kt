package com.example.xpivo.data.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("middleName")
    val middleName: String? = "",

    @SerializedName("birthDate")
    val birthDate: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("passwordHash")
    val passwordHash: String,

    @SerializedName("rememberMe")
    val rememberMe: Boolean
)
