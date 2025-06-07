package com.example.xpivo.data.cache

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreCache(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "app_cache")
        private val AUTH_Token = stringPreferencesKey("auth_token")
    }

    suspend fun saveToken(token:String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_Token] = token
        }
    }

    val tokenFlow:Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[AUTH_Token]
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}