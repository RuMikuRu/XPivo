package com.example.xpivo.feature.login_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.xpivo.R
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.navigation.Screen
import com.example.xpivo.ui.components.PrimaryBasicTextField
import com.example.xpivo.ui.components.PrimaryButton
import com.example.xpivo.ui.components.PrimaryCheckBox
import com.example.xpivo.ui.components.PrimaryPasswordTextField
import com.example.xpivo.ui.theme.RegularStyle
import com.example.xpivo.ui.theme.SmallTextStyle
import com.example.xpivo.ui.theme.TitleStyle

@Composable
fun LoginPage(
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>(),
    navController: NavHostController,
    onClickLogin: () -> Unit = {},
    onClickRegistration: () -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rememberMe by rememberSaveable { mutableStateOf(false) }
    val loginState by viewModel.loginState.collectAsState()
    when(val state = loginState) {
        is Lce.Content<Boolean?> -> {
            if (state.data == true) {
                navController.popBackStack()
                navController.navigate(Screen.ArticlesPage.route)
            }
        }
        is Lce.Error -> {}
        Lce.Loading -> {}
        is Lce.Ready<*> -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(R.drawable.pivo_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "С возвращением", style = TitleStyle, modifier = Modifier.fillMaxWidth())
                    PrimaryBasicTextField(
                        value = email,
                        placeholder = "Email",
                        onValueChange = { it -> email = it })
                    PrimaryPasswordTextField(
                        value = password,
                        placeholder = "Пароль",
                        onValueChange = { it -> password = it })
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Запомнить меня", style = RegularStyle, modifier = Modifier.weight(1f))
                        PrimaryCheckBox(checked = rememberMe, onCheckedChange = {it ->  rememberMe = it})
                    }
                    PrimaryButton(
                        title = "Войти",
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        viewModel.login(email, password, rememberMe)
                    }
                    TextButton(onClickRegistration) {
                        Text(
                            text = "Нет аккаунта? Зарегистрироваться",
                            style = SmallTextStyle,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun PreviewLoginPage() {
    //  LoginPage()
}