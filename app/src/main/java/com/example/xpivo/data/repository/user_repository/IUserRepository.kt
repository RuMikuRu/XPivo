package com.example.xpivo.data.repository.user_repository

import com.example.xpivo.data.model.User

interface IUserRepository {
    suspend fun userRegistration(user: User)
    suspend fun login(email: String, password: String, rememberMe: Boolean) : Boolean
    suspend fun logout(): Boolean
    suspend fun getUser(): User
    suspend fun updateUser(user: User) : User
}