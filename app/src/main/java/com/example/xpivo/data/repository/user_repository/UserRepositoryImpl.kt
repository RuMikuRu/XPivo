package com.example.xpivo.data.repository.user_repository

import android.util.Log
import com.example.xpivo.core.util.log
import com.example.xpivo.data.cache.DataStoreCache
import com.example.xpivo.data.cache.FreeCache
import com.example.xpivo.data.model.User
import com.example.xpivo.network.Service
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val service: Service,
    private val dataStore: DataStoreCache
) : IUserRepository {
    override suspend fun userRegistration(user: User) {
        service.register(user)
    }

    override suspend fun login(email: String, password: String, rememberMe: Boolean): Boolean {
        val token = service.login(email, password).userToken
        this.log("token = $token")
        Log.d("UserRepository", "login: $token")
        return if (token.isNotBlank()) {
            if (rememberMe) {
                dataStore.saveToken(token)
            } else {
                FreeCache.token = token
            }
            true
        } else {
            false
        }
    }
}