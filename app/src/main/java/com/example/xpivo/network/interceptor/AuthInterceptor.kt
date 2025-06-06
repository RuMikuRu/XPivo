package com.example.xpivo.network.interceptor

import com.example.xpivo.core.AuthRequired
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class AuthInterceptor(
    private val tokenProvider: () -> String?
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authRequired = request.tag(Invocation::class.java)
            ?.method()
            ?.getAnnotation(AuthRequired::class.java) != null

        return if (authRequired) {
            val token = tokenProvider()
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(request)
        }
    }
}