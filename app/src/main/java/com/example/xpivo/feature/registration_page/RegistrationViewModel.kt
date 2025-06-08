package com.example.xpivo.feature.registration_page

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.xpivo.core.view_model.BaseViewModel
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.model.Gender
import com.example.xpivo.data.model.User
import com.example.xpivo.data.repository.user_repository.IUserRepository
import com.example.xpivo.data.request.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val userRepository: IUserRepository
) : BaseViewModel(context) {

    private val _registrationState = MutableStateFlow<Lce<Unit>>(Lce.Ready(Unit))
    val registrationState: StateFlow<Lce<Unit>> = _registrationState.asStateFlow()

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var middleName by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var gender by mutableStateOf("")
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var rememberMe by mutableStateOf(false)

    fun registerUser() {
        _registrationState.value = Lce.Loading

        launchSafely {
            val request = User(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName.takeIf { it.isNotBlank() } ?: "",
                birthDate = birthDate,
                gender = if (gender == "Мужской") Gender.MALE else Gender.FEMALE,
                userName = username,
                email = email,
                password = password,
                rememberMe = rememberMe
            )
            userRepository.userRegistration(request)
            _registrationState.value = Lce.Content(Unit)
        }
    }

    fun resetRegistrationState() {
        _registrationState.value = Lce.Ready(Unit)
    }
}