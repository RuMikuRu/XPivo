package com.example.xpivo.feature.user_profile_page

import android.content.Context
import com.example.xpivo.core.view_model.BaseViewModel
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.model.Gender
import com.example.xpivo.data.model.User
import com.example.xpivo.data.repository.user_repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    repository: IUserRepository
) : BaseViewModel(context) {

    private val _firstName = MutableStateFlow("")
    val firstName = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName = _lastName.asStateFlow()

    private val _middleName = MutableStateFlow("")
    val middleName = _middleName.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate = _birthDate.asStateFlow()

    private val _gender = MutableStateFlow(Gender.MALE)
    val gender = _gender.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _oldPassword = MutableStateFlow("")
    val oldPassword = _oldPassword.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword = _newPassword.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _rememberMe = MutableStateFlow(false)
    val rememberMe = _rememberMe.asStateFlow()


    init {

    }

    fun validatePassword(newPassword:String, confirmPassword:String): Boolean {
        if (newPassword != confirmPassword) return false
        if (newPassword.length < 8) return false
        return true
    }

    fun onFirstNameChange(value: String) { _firstName.value = value }
    fun onLastNameChange(value: String) { _lastName.value = value }
    fun onMiddleNameChange(value: String) { _middleName.value = value }
    fun onBirthDateChange(value: String) { _birthDate.value = value }
    fun onGenderChange(value: Gender) { _gender.value = value }
    fun onUserNameChange(value: String) { _userName.value = value }
    fun onEmailChange(value: String) { _email.value = value }
    fun onOldPasswordChange(value: String) { _oldPassword.value = value }
    fun onRememberMeChange(value: Boolean) { _rememberMe.value = value }

    fun buildUser(): User = User(
        firstName = _firstName.value,
        lastName = _lastName.value,
        middleName = _middleName.value,
        birthDate = _birthDate.value,
        gender = _gender.value,
        userName = _userName.value,
        email = _email.value,
        password = _newPassword.value,
        rememberMe = _rememberMe.value
    )
}