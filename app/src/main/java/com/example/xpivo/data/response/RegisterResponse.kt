package com.example.xpivo.data.response

import com.google.gson.annotations.SerializedName

class RegisterResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("middleName")
    val middleName: String,

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

    @SerializedName("photo")
    val photo: String?,

    @SerializedName("rememberMe")
    val rememberMe: Boolean,

    @SerializedName("createdAt")
    val createdAt: String
)