package com.example.xpivo.data.response

import com.example.xpivo.data.model.Gender
import com.example.xpivo.data.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("username")
    val userName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("middleName")
    val middleName: String?,

    @SerializedName("birthDate")
    val birthDate: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("passwordHash")
    val passwordHash: String,

    @SerializedName("photo")
    val photo: String?,

    @SerializedName("rememberMe")
    val rememberMe: Boolean,

    @SerializedName("createdAt")
    val createdAt: String,
)

fun UserResponse.toUser(): User {
    return User(
        userName = userName,
        email = email,
        lastName = lastName,
        firstName = firstName,
        middleName = middleName ?: "",
        birthDate = birthDate,
        gender = when (gender) {
            "Мужской" -> Gender.MALE
            "Женский" -> Gender.FEMALE
            else -> throw IllegalArgumentException("Unknown gender: $gender")
        },
        password = "",
        photo = photo,
        rememberMe = rememberMe,
    )
}
