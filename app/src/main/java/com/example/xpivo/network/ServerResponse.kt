package com.example.xpivo.network

import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response
import java.io.IOException

class ServerResponse(private val gson: Gson) {

    @Throws(IOException::class)
    suspend fun <T> handleCode(response: Response<T>): T {
        if (response.isSuccessful) {
            return response.body() ?: throw Exception()
        } else {
            val errorBody = response.errorBody()?.string().orEmpty()

            val errorMessage = extractErrorMessage(errorBody)

            throw ApiErrorException(
                message = errorMessage,
                code = response.code(),
                rawError = errorBody
            )
        }
    }

    private fun extractErrorMessage(errorJson: String): String {
        // Пытаемся вытащить message поле из JSON, если оно есть
        return try {
            val jsonObject = gson.fromJson(errorJson, JsonObject::class.java)
            jsonObject["message"]?.asString ?: "Unknown error"
        } catch (e: Exception) {
            "Unknown error"
        }
    }
}

class ApiErrorException(
    override val message: String,
    val code: Int,
    val rawError: String
) : IOException()