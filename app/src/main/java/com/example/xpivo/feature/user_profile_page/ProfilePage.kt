package com.example.xpivo.feature.user_profile_page

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
                    value = userData?.firstName ?: "",
                    onValueChange = { userData?.firstName = it },
                    title = "Фамилия",
                    placeholder = "Введите вашу фамилию"
                )
                PrimaryBasicTextField(
                    value = viewModel.lastName,
                    onValueChange = { viewModel.lastName = it },
                    title = "Имя",
                    placeholder = "Введите ваше имя"
                )
                PrimaryBasicTextField(
                    value = viewModel.middleName,
                    onValueChange = { viewModel.middleName = it },
                    title = "Отчество (опционально)",
                    placeholder = "Введите ваше отчество"
                )

                PrimaryBasicTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    title = "Email",
                    placeholder = "Введите ваш email"
                )
                PrimaryPasswordTextField(
                    value = viewModel.oldPassword,
                    placeholder = "Введите старый пароль",
                    onValueChange = { viewModel.oldPassword = it })

                PrimaryPasswordTextField(
                    value = viewModel.newPassword,
                    placeholder = "Введите новый пароль",
                    onValueChange = { viewModel.newPassword = it })

                PrimaryPasswordTextField(
                    value = viewModel.confirmPassword,
                    placeholder = "Подтвердите новый пароль",
                    onValueChange = { viewModel.confirmPassword = it })

                PrimaryBasicTextField(
                    value = viewModel.birthDate,
                    onValueChange = { viewModel.birthDate = it },
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
                    onValueChange = { key, value -> viewModel.gender = value }
                )
                ActionRowLayout {
                    PrimaryButton(title = "Сохранить изменения", modifier = Modifier.fillMaxWidth()) {
                        viewModel.registerUser()
                    }
                    PrimaryButton(
                        title = "Закрыть",
                        modifier = Modifier.fillMaxWidth(),
                        colorType = PrimaryBeige
                    ) {
                        activity?.onBackPressed()
                    }
                }
                TextButton(onClick = {}) {
                    Text(text = "Сохранить в PDF", style = SmallTitleStyle)
                }
            }
        }
        is Lce.Error -> TODO()
        Lce.Loading -> TODO()
        is Lce.Ready<*> -> TODO()
    }


}