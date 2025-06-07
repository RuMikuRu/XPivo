package com.example.xpivo.navigation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.example.xpivo.core.view_model.BaseViewModel
import com.example.xpivo.data.cache.DataStoreCache
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val dataStoreCache: DataStoreCache
) : BaseViewModel(context) {
    private val _uiState = MutableStateFlow(false)
    val uiState = _uiState.asStateFlow()

    private val _authToken = MutableStateFlow("")
    val authToken = _authToken.asStateFlow()

    init {
        viewModelScope.launch {
            val token = dataStoreCache.tokenFlow.first()
            _authToken.value = token ?: ""
            _uiState.value = true
        }
    }
}