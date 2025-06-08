package com.example.xpivo.feature.registration_page

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.example.xpivo.R
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.navigation.Screen
import com.example.xpivo.ui.components.ActionLayout
import com.example.xpivo.ui.components.ImageProfile
import com.example.xpivo.ui.components.PrimaryBasicTextField
import com.example.xpivo.ui.components.PrimaryButton
import com.example.xpivo.ui.components.PrimaryDropDownMenu
import com.example.xpivo.ui.components.PrimaryPasswordTextField
import com.example.xpivo.ui.theme.LargeStyle
import com.example.xpivo.ui.theme.PrimaryBeige
import com.example.xpivo.ui.theme.SmallTextStyle

private val GENDER = mapOf(0 to "Мужской", 1 to "Женский")

@Composable
fun RegistrationPage(
    navController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val registrationState by viewModel.registrationState.collectAsState()
    val activity = LocalActivity.current

    // Переход при успешной регистрации
    LaunchedEffect(registrationState) {
        if (registrationState is Lce.Content) {
            Log.d("RegistrationPage", "RegistrationPage: navigate ")
            navController.navigate(Screen.LoginPage.route) {
                popUpTo(Screen.RegistrationPage.route) { inclusive = true }
            }
        }
    }

    // UI в зависимости от состояния
    when (registrationState) {
        is Lce.Error -> {
            val errorMessage = (registrationState as Lce.Error).throwable.message ?: "Ошибка регистрации"
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = errorMessage, color = Color.Red)
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryButton(title = "Повторить") {
                    viewModel.registerUser()
                }
            }
        }

        Lce.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Фото профиля", style = LargeStyle)
                ImageProfile { }
                Text(text = "Макс. размер: 2MB", style = SmallTextStyle)

                PrimaryBasicTextField(
                    value = viewModel.firstName,
                    onValueChange = { viewModel.firstName = it },
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
                PrimaryBasicTextField(
                    value = viewModel.username,
                    onValueChange = { viewModel.username = it },
                    title = "Имя пользователя",
                    placeholder = "Введите имя пользователя"
                )
                PrimaryBasicTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    title = "Email",
                    placeholder = "Введите ваш email"
                )
                PrimaryPasswordTextField(
                    value = viewModel.password,
                    placeholder = "Введите пароль",
                    onValueChange = { viewModel.password = it }
                )
                Text(
                    text = "Пароль должен быть длиной не менее 8 символов и включать цифру, специальный символ и заглавную букву.",
                    style = SmallTextStyle
                )

                ActionLayout {
                    PrimaryButton(title = "Регистрация", modifier = Modifier.fillMaxWidth()) {
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
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRegistrationPage() {
    //RegistrationPage()
}