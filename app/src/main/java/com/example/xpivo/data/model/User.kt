package com.example.xpivo.data.model

data class User(
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val birthDate: String,
    val gender: Gender,
    val userName: String,
    val email: String,
    val password:String,
    val rememberMe: Boolean
) {
    fun getGender() = gender.name
}

enum class Gender(value: String) {
    MALE("Мужской"),
    FEMALE("Женский")
}
