package com.example.xpivo

import android.content.Context
import com.example.xpivo.core.view_model.BaseViewModel
import com.example.xpivo.data.repository.user_repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val repository: IUserRepository
) : BaseViewModel(context) {

    private val _logoutResult = MutableStateFlow<Boolean?>(null)
    val logoutResult: SharedFlow<Boolean?> = _logoutResult.asStateFlow()

    fun logout() {
        launchSafely {
            val success = repository.logout()
            _logoutResult.update { success }
        }
    }
}