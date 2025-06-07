package com.example.xpivo.core.util

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

suspend fun <T> executor(block: () -> T): T {
    return withContext(Dispatchers.IO) {
        try {
            block()
        } catch (e: Exception) {
            throw e
        }
    }
}

class NetworkState(private val context: Context) {
    fun hasOnlineNetwork(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}