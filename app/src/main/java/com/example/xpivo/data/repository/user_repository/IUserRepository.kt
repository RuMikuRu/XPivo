package com.example.xpivo.data.repository.user_repository

import com.example.xpivo.data.model.User

interface IUserRepository {
    suspend fun userRegistration(user: User)
    suspend fun login(email:String, password:String) : Boolean
}