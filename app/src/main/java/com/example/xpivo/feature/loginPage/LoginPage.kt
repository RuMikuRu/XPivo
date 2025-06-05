package com.example.xpivo.feature.loginPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xpivo.R
import com.example.xpivo.ui.components.PrimaryBasicTextField
import com.example.xpivo.ui.components.PrimaryButton
import com.example.xpivo.ui.components.PrimaryCheckBox
import com.example.xpivo.ui.components.PrimaryPasswordTextField
import com.example.xpivo.ui.theme.RegularStyle
import com.example.xpivo.ui.theme.SmallTextStyle
import com.example.xpivo.ui.theme.TitleStyle

@Composable
fun LoginPage() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.pivo_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "С возвращением", style = TitleStyle, modifier = Modifier.fillMaxWidth())
            PrimaryBasicTextField(value = "", placeholder = "Имя пользователя", onValueChange = {})
            PrimaryPasswordTextField(value = "", placeholder = "Пароль", onValueChange = {})
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Запомнить меня", style = RegularStyle, modifier = Modifier.weight(1f))
                PrimaryCheckBox(checked = false, onCheckedChange = {})
            }
            PrimaryButton(title = "Войти", modifier = Modifier.fillMaxWidth()) { }
            Text(
                text = "Нет аккаунта? Зарегистрироваться",
                style = SmallTextStyle,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun PreviewLoginPage() {
    LoginPage()
}