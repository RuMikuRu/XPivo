package com.example.xpivo.network

import com.example.xpivo.core.AuthRequired
import com.example.xpivo.data.request.LoginRequest
import com.example.xpivo.data.request.RegisterRequest
import com.example.xpivo.data.response.LoginResponse
import com.example.xpivo.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ServerApi {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("logout")
    @AuthRequired
    suspend fun logout()


}