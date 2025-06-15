package com.example.xpivo.network

import android.util.Log
import com.example.xpivo.core.util.NetworkState
import com.example.xpivo.data.model.User
import com.example.xpivo.data.model.toUserUpdateRequest
import com.example.xpivo.data.request.CreateArticleRequest
import com.example.xpivo.data.request.DetailArticleRequest
import com.example.xpivo.data.request.LoginRequest
import com.example.xpivo.data.request.RegisterRequest
import com.example.xpivo.data.response.Article
import com.example.xpivo.data.response.DetailArticleResponse
import com.example.xpivo.data.response.LoginResponse
import com.example.xpivo.data.response.toUser
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
            rememberMe = user.rememberMe,
            photo = user.photo
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

    suspend fun logout() : Boolean {
        return serverApi.logout()
    }

    suspend fun getUserById(id: Int): User {
        try {
            return serverApi.getUserById(id).toUser()
        } catch (e: Exception) {
            Log.d("Service", "getUserById: exception")
            throw e
        }
    }

    suspend fun updateUser(id:Long, user: User): User {
        return serverApi.updateUser(id = id, user = user.toUserUpdateRequest()).toUser()
    }

    suspend fun getDetailArticle(id: Int): DetailArticleResponse {
        return serverApi.getArticleById(id)
    }

    suspend fun getArticles(): List<Article> {
        return serverApi.getArticles()
    }

    suspend fun updateArticle(id: Int, article: CreateArticleRequest) {
        serverApi.updateArticle(id, article)
    }

    suspend fun getArticleByUserId(id: Int): List<DetailArticleResponse> {
        return serverApi.getArticlesByAuthorId(id)
    }

    suspend fun createArticle(article: CreateArticleRequest) {
        serverApi.createArticle(article)
    }
}


