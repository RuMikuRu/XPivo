package com.example.xpivo.network

import com.example.xpivo.core.AuthRequired
import com.example.xpivo.data.request.CreateArticleRequest
import com.example.xpivo.data.request.LoginRequest
import com.example.xpivo.data.request.RegisterRequest
import com.example.xpivo.data.request.UserUpdateRequest
import com.example.xpivo.data.response.Article
import com.example.xpivo.data.response.DetailArticleResponse
import com.example.xpivo.data.response.LoginResponse
import com.example.xpivo.data.response.RegisterResponse
import com.example.xpivo.data.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServerApi {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("logout")
    @AuthRequired
    suspend fun logout() : Boolean

    @GET("user/{id}")
    @AuthRequired
    suspend fun getUserById(@Path("id") id: Int) : UserResponse

    @PUT("user/{id}")
    @AuthRequired
    suspend fun updateUser(@Path("id") id: Long, @Body user: UserUpdateRequest): UserResponse

    @GET("articles")
    @AuthRequired
    suspend fun getArticles(): List<Article>

    @POST("article")
    @AuthRequired
    suspend fun createArticle(@Body articleRequest: CreateArticleRequest)

    @GET("article/{id}")
    @AuthRequired
    suspend fun getArticleById(@Path("id") id: Int): DetailArticleResponse

    @GET("article/author/{authorId}")
    @AuthRequired
    suspend fun getArticlesByAuthorId(@Path("authorId") authorId: Int): List<DetailArticleResponse>

    @PUT("article/{id}")
    @AuthRequired
    suspend fun updateArticle(@Path("id") id:Int , @Body request: CreateArticleRequest)
}