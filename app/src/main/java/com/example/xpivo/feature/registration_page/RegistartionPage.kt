package com.example.xpivo.feature.registration_page

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.xpivo.R
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
fun RegistrationPage() {
    val activity = LocalActivity.current
    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Фото профиля", style = LargeStyle)
        ImageProfile { }
        Text(text = "Макс. размер: 2MB", style = SmallTextStyle)
        PrimaryBasicTextField(
            value = "",
            onValueChange = {},
            title = "Фамилия",
            placeholder = "Введите вашу фамилию"
        )
        PrimaryBasicTextField(
            value = "",
            onValueChange = {},
            title = "Имя",
            placeholder = "Введите ваше имя"
        )

        PrimaryBasicTextField(
            value = "",
            onValueChange = {},
            title = "Отчество (опционально)",
            placeholder = "Введите ваше отчество"
        )

        PrimaryBasicTextField(
            value = "",
            onValueChange = {},
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

        PrimaryDropDownMenu(titleContent = GENDER, onValueChange = { key, value -> })

        PrimaryBasicTextField(
            value = "",
            onValueChange = {},
            title = "Имя пользователя",
            placeholder = "Введите имя пользователя"
        )

        PrimaryBasicTextField(
            value = "",
            onValueChange = {},
            title = "Email",
            placeholder = "Введите ваш email"
        )

        PrimaryPasswordTextField(value = "", placeholder = "Введите пароль", onValueChange = {})
        Text(
            text = "Пароль должен быть длиной не менее 8 символов и включать цифру, специальный символ и заглавную букву.",
            style = SmallTextStyle
        )
        ActionLayout {
            PrimaryButton(title = "Регистрация", modifier = Modifier.fillMaxWidth()) { }
            PrimaryButton(
                title = "Закрыть",
                modifier = Modifier.fillMaxWidth(),
                colorType = PrimaryBeige
            ) { activity?.onBackPressed() }
        }
    }
}

@Preview
@Composable
private fun PreviewRegistrationPage() {
    RegistrationPage()
}