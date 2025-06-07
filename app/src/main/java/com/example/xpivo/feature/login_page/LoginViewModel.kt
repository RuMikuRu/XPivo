package com.example.xpivo.feature.login_page

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.example.xpivo.core.view_model.BaseViewModel
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.repository.user_repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val userRepository: IUserRepository
) : BaseViewModel(context) {
    val loginState = MutableStateFlow<Lce<Boolean?>>(Lce.Content(null))

    fun login(email:String, password: String) {
        val newStateFlow = stateFlowWithLce(block = {
            Log.d("LoginViewModel", "login: tyt")
            userRepository.login(email, password)
        })

        viewModelScope.launch {
            newStateFlow.collect { state ->
                loginState.update { state }
            }
        }
    }
}