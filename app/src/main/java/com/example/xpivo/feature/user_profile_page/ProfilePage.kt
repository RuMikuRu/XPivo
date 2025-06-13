package com.example.xpivo.feature.user_profile_page

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xpivo.R
import com.example.xpivo.feature.registration_page.GENDER
import androidx.compose.runtime.getValue
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.model.User
import com.example.xpivo.ui.components.ActionRowLayout
import com.example.xpivo.ui.components.ImageProfile
import com.example.xpivo.ui.components.PrimaryBasicTextField
import com.example.xpivo.ui.components.PrimaryButton
import com.example.xpivo.ui.components.PrimaryDropDownMenu
import com.example.xpivo.ui.components.PrimaryPasswordTextField
import com.example.xpivo.ui.theme.PrimaryBeige
import com.example.xpivo.ui.theme.SmallTitleStyle

@Composable
fun ProfilePage(viewModel: ProfileViewModel = hiltViewModel()) {
    val activity = LocalActivity.current
    val userState by viewModel.user.collectAsState()

    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val middleName by viewModel.middleName.collectAsState()
    val email by viewModel.email.collectAsState()
    val birthDate by viewModel.birthDate.collectAsState()

    val oldPassword by viewModel.oldPassword.collectAsState()
    val newPassword by viewModel.newPassword.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()

    when (userState) {
        is Lce.Content<User?> -> {
            val userData = (userState as Lce.Content<User?>).data
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageProfile { }

                PrimaryBasicTextField(
                    value = firstName,
                    onValueChange = { viewModel.onFirstNameChange(it) },
                    title = "Фамилия",
                    placeholder = "Введите вашу фамилию"
                )
                PrimaryBasicTextField(
                    value = lastName,
                    onValueChange = { viewModel.onLastNameChange(it) },
                    title = "Имя",
                    placeholder = "Введите ваше имя"
                )
                PrimaryBasicTextField(
                    value = middleName,
                    onValueChange = { viewModel.onMiddleNameChange(it) },
                    title = "Отчество (опционально)",
                    placeholder = "Введите ваше отчество"
                )

                PrimaryBasicTextField(
                    value = email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    title = "Email",
                    placeholder = "Введите ваш email"
                )
                PrimaryPasswordTextField(
                    value = oldPassword,
                    placeholder = "Введите старый пароль",
                    onValueChange = { viewModel.onOldPasswordChange(it) })

                PrimaryPasswordTextField(
                    value = newPassword,
                    placeholder = "Введите новый пароль",
                    onValueChange = { viewModel.onNewPasswordChange(it) })

                PrimaryPasswordTextField(
                    value = confirmPassword,
                    placeholder = "Подтвердите новый пароль",
                    onValueChange = { viewModel.onConfirmPasswordChange(it) })

                PrimaryBasicTextField(
                    value = birthDate,
                    onValueChange = { viewModel.onBirthDateChange(it) },
                    title = "день рождения",
                    placeholder = "Введите ваш день рождения",
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_calendar),
                            tint = Color.Unspecified,
                            contentDescription = null
                        )
                    }
                )
                PrimaryDropDownMenu(
                    titleContent = GENDER,
                    onValueChange = { key, value ->
                        viewModel.onGenderChange(
                            when (key) {
                                0 -> com.example.xpivo.data.model.Gender.MALE
                                else -> com.example.xpivo.data.model.Gender.FEMALE
                            }
                        )
                    }
                )
                ActionRowLayout {
                    PrimaryButton(
                        title = "Сохранить изменения",
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        viewModel.updateUser()
                    }
                    PrimaryButton(
                        title = "Закрыть",
                        modifier = Modifier.fillMaxWidth(),
                        colorType = PrimaryBeige
                    ) {
                        activity?.onBackPressed()
                    }
                }
                TextButton(onClick = {

                }) {
                    Text(text = "Сохранить в PDF", style = SmallTitleStyle)
                }
            }
        }

        is Lce.Error -> {
            Text(text = "Error loading user data")
        }

        Lce.Loading -> {
            CircularProgressIndicator()
        }

        is Lce.Ready<*> -> {
        }

    }


}