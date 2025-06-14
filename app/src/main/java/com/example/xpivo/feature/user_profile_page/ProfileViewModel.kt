package com.example.xpivo.feature.user_profile_page

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import androidx.core.graphics.scale

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

    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveProfileToPdfToDownloads() {
        viewModelScope.launch {
            val user = buildUser()
            val fullName = "${user.lastName} ${user.firstName} ${user.middleName}"
            val birth = user.birthDate
            val gender = when (user.gender) {
                Gender.MALE -> "Мужской"
                Gender.FEMALE -> "Женский"
            }

            val qrText = "$fullName\n$birth\n$gender"
            val qrBitmap = generateQrCode(qrText)

            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas: Canvas = page.canvas
            val paint = Paint()

            paint.textSize = 18f
            canvas.drawText("Профиль пользователя", 40f, 50f, paint)

            paint.textSize = 14f
            var y = 100f
            canvas.drawText("ФИО: $fullName", 40f, y, paint); y += 30
            canvas.drawText("Дата рождения: $birth", 40f, y, paint); y += 30
            canvas.drawText("Пол: $gender", 40f, y, paint); y += 50

            qrBitmap?.let {
                val resized = it.scale(200, 200)
                canvas.drawBitmap(resized, 40f, y, paint)
            }

            pdfDocument.finishPage(page)

            val filename = "profile_${System.currentTimeMillis()}.pdf"
            val mimeType = "application/pdf"

            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, filename)
                put(MediaStore.Downloads.MIME_TYPE, mimeType)
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            try {
                uri?.let {
                    resolver.openOutputStream(it)?.use { outputStream ->
                        pdfDocument.writeTo(outputStream)
                    }
                    Toast.makeText(context, "PDF сохранён в Загрузки", Toast.LENGTH_LONG).show()
                } ?: run {
                    Toast.makeText(context, "Ошибка при создании PDF", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Ошибка сохранения: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                pdfDocument.close()
            }
        }
    }

    private fun generateQrCode(text: String): Bitmap? {
        return try {
            val size = 512
            val bitMatrix = MultiFormatWriter().encode(
                text, BarcodeFormat.QR_CODE, size, size, null
            )
            val width = bitMatrix.width
            val height = bitMatrix.height
            val pixels = IntArray(width * height)

            for (y in 0 until height) {
                for (x in 0 until width) {
                    val black = Color.BLACK
                    val white = Color.WHITE
                    pixels[y * width + x] = if (bitMatrix.get(x, y)) black else white
                }
            }

            Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565)
        } catch (e: Exception) {
            null
        }
    }
}