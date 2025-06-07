package com.example.xpivo.data.cache

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
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
        private val REMEMBER_ME = booleanPreferencesKey("remember_me")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_Token] = token
        }
    }

    val tokenFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[AUTH_Token]
    }

    suspend fun saveRememberMe(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[REMEMBER_ME] = value
        }
    }

    val saveRemember: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[REMEMBER_ME]
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}