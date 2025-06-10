package com.example.xpivo.network

import com.example.xpivo.core.AuthRequired
import com.example.xpivo.data.request.LoginRequest
import com.example.xpivo.data.request.RegisterRequest
import com.example.xpivo.data.response.Article
import com.example.xpivo.data.response.DetailArticleResponse
import com.example.xpivo.data.response.LoginResponse
import com.example.xpivo.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerApi {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("logout")
    @AuthRequired
    suspend fun logout()

    @GET("articles")
    @AuthRequired
    suspend fun getArticles(): List<Article>

    @GET("article/{id}")
    @AuthRequired
    suspend fun getArticleById(@Path("id") id: Int): DetailArticleResponse

}