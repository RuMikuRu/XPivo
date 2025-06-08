package com.example.xpivo.network

import android.util.Log
import com.example.xpivo.core.util.NetworkState
import com.example.xpivo.data.model.User
import com.example.xpivo.data.request.LoginRequest
import com.example.xpivo.data.request.RegisterRequest
import com.example.xpivo.data.response.Article
import com.example.xpivo.data.response.LoginResponse
import retrofit2.Call
import java.io.IOException

class Service(
    private val serverApi: ServerApi,
    private val networkState: NetworkState,
    val serverResponse: ServerResponse
) {

    suspend fun register(user: User) {
        val request = RegisterRequest(
            firstName = user.firstName,
            lastName = user.lastName,
            middleName = user.middleName,
            birthDate = user.birthDate,
            gender = user.getGender(),
            username = user.userName,
            email = user.email,
            passwordHash = user.password,
            rememberMe = user.rememberMe
        )
        serverApi.register(request)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        try {
            val request = LoginRequest(email = email, password = password)
            return serverApi.login(request)
        } catch (e: Exception) {
            Log.e("Service", "login: error before execute", e)
            throw e
        }
    }

    suspend fun getArticles() : List<Article> {
        return serverApi.getArticles()
    }
}