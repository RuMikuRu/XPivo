package com.example.xpivo.data.repository.user_repository

import android.util.Log
import com.example.xpivo.core.util.log
import com.example.xpivo.data.cache.DataStoreCache
import com.example.xpivo.data.cache.FreeCache
import com.example.xpivo.data.model.User
import com.example.xpivo.network.Service
import kotlinx.coroutines.flow.first
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

    override suspend fun logout(): Boolean {
        dataStore.clear()
        FreeCache.clear()
        val response =  service.logout()
        if (response) {
            dataStore.clear()
            FreeCache.clear()
            return true
        } else {
            return false
        }
    }

    override suspend fun getUser(): User {
        val userId = dataStore.userId.first() ?: FreeCache.userId ?: throw Exception("No user id")
        return service.getUserById(userId)
    }

    override suspend fun updateUser(user: User): User {
        val userId = dataStore.userId.first() ?: FreeCache.userId ?: throw Exception("No user id")
        return service.updateUser(userId.toLong(), user)
    }
}