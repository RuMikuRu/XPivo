package com.example.xpivo.network

import com.example.xpivo.core.util.NetworkState
import com.example.xpivo.data.model.User
import com.example.xpivo.data.request.LoginRequest
import com.example.xpivo.data.request.RegisterRequest
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
        execute(serverApi.register(request))
    }

    suspend fun login(email:String, password:String): LoginResponse {
        val request = LoginRequest(email = email, password = password)
        return execute(serverApi.login(request))
    }

    suspend fun <T> execute(tCall: Call<T>): T {
        try {
            if (networkState.hasOnlineNetwork()) {
                return serverResponse.handleCode(tCall.execute())
            }
            throw Exception()
        } catch (e: IOException) {
            throw Exception(e.message)
        }
    }
}