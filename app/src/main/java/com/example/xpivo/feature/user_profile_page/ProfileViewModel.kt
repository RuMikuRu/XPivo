package com.example.xpivo.feature.user_profile_page

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewModelScope
import com.example.xpivo.core.util.base64ToImageBitmap
import com.example.xpivo.core.util.imageBitmapToBase64
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val repository: IUserRepository
) : BaseViewModel(context) {

    private val _user = MutableStateFlow<Lce<User?>>(Lce.Ready(null))
    val user = _user.asStateFlow()

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

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private val _profileImage = MutableStateFlow<ImageBitmap?>(null)
    val profileImage = _profileImage.asStateFlow()


    init {
        getUser()
    }

    fun validatePassword(newPassword:String, confirmPassword:String): Boolean {
        if (newPassword != confirmPassword) return false
        if (newPassword.length < 8) return false
        return true
    }

    fun onImageSelected(uri: Uri, context: Context) {
        viewModelScope.launch {
            try {
                val stream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(stream)
                _profileImage.value = bitmap.asImageBitmap()
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }


    fun getUser() {
        launchSafely {
            _user.value = Lce.Loading
            try {
                val loadedUser = repository.getUser()
                _user.value = Lce.Content(loadedUser)
                loadedUser.let {
                    _firstName.value = it.firstName
                    _lastName.value = it.lastName
                    _middleName.value = it.middleName
                    _birthDate.value = it.birthDate
                    _gender.value = it.gender
                    _email.value = it.email
                    _userName.value = it.userName
                    _rememberMe.value = it.rememberMe
                    _profileImage.value = it.photo?.let { base64 -> base64ToImageBitmap(base64) }
                }
            } catch (e: Exception) {
                _user.value = Lce.Error(e)
                sendError(e)
            }
        }
    }

    fun updateUser() {
        launchSafely {
            if(_newPassword.value != "" && _confirmPassword.value == "") {
                if (!validatePassword(
                        _newPassword.value,
                        _confirmPassword.value
                    )
                ) return@launchSafely
            }
            _user.value = Lce.Loading
            repository.updateUser(buildUser())
            getUser()
        }

    }

    fun onFirstNameChange(value: String) { _firstName.value = value }
    fun onLastNameChange(value: String) { _lastName.value = value }
    fun onMiddleNameChange(value: String) { _middleName.value = value }
    fun onBirthDateChange(value: String) { _birthDate.value = value }
    fun onGenderChange(value: Gender) { _gender.value = value }
    fun onEmailChange(value: String) { _email.value = value }
    fun onOldPasswordChange(value: String) { _oldPassword.value = value }
    fun onNewPasswordChange(value: String) { _newPassword.value = value }
    fun onConfirmPasswordChange(value: String) { _confirmPassword.value = value }
    fun onRememberMeChange(value: Boolean) { _rememberMe.value = value }

    fun buildUser(): User = User(
        photo = _profileImage.value?.let { imageBitmapToBase64(it) } ?: "",
        firstName = _firstName.value,
        lastName = _lastName.value,
        middleName = _middleName.value,
        birthDate = _birthDate.value,
        gender = _gender.value,
        email = _email.value,
        userName = _userName.value,
        password = _newPassword.value,
        rememberMe = _rememberMe.value
    )
}