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
        val response = service.login(email, password)
        this.log("token = ${response.userToken}")
        Log.d("UserRepository", "login: $response")
        return if (response.userToken.isNotBlank()) {
            if (rememberMe) {
                dataStore.saveToken(response.userToken)
                dataStore.saveUserId(response.userId)
            } else {
                FreeCache.token = response.userToken
                FreeCache.userId = response.userId
            }
            true
        } else {
            false
        }
    }
}