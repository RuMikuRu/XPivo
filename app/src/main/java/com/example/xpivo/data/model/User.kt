package com.example.xpivo.data.model

import com.example.xpivo.data.request.UserUpdateRequest

data class User(
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val birthDate: String,
    val gender: Gender,
    val userName: String,
    val email: String,
    val password:String,
    val rememberMe: Boolean,
    val photo: String? = null
) {
    fun getGender() = gender.value
}

enum class Gender(val value: String) {
    MALE("Мужской"),
    FEMALE("Женский")
}

fun User.toUserUpdateRequest(): UserUpdateRequest {
    return UserUpdateRequest(
        firstName = firstName,
        lastName = lastName,
        middleName = middleName,
        birthDate = birthDate,
        email = email,
        username = userName,
        password = password,
        photo = photo ?: ""
    )
}
