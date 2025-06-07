package com.example.xpivo.data.repository.user_repository

import android.util.Log
import com.example.xpivo.core.util.log
import com.example.xpivo.data.cache.DataStoreCache
import com.example.xpivo.data.model.User
import com.example.xpivo.data.request.LoginRequest
import com.example.xpivo.data.request.RegisterRequest
import com.example.xpivo.network.ServerApi
import com.example.xpivo.network.Service
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val service: Service,
    private val dataStore: DataStoreCache
) : IUserRepository {
    override suspend fun userRegistration(user: User) {
        service.register(user)
    }

    override suspend fun login(email: String, password: String): Boolean {
        val token = service.login(email, password).userToken
        this.log("token = $token")
        return if (token.isNotBlank()) {
            dataStore.saveToken(token)
            true
        } else {
            false
        }
    }
}