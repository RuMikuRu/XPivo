package com.example.xpivo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xpivo.feature.articles_page.ArticlesPage
import com.example.xpivo.feature.login_page.LoginPage
import com.example.xpivo.feature.registration_page.RegistrationPage

@Composable
fun PrimaryNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = Screen.LoginPage.route){
        composable(Screen.LoginPage.route) {
            LoginPage(
                onClickLogin = {
                    navController.navigate(Screen.ArticlesPage.route)
                },
                onClickRegistration = {
                    navController.navigate(Screen.RegistrationPage.route)
                }
            )
        }
        composable(Screen.RegistrationPage.route) {
            RegistrationPage()
        }
        composable(Screen.ArticlesPage.route) {
            ArticlesPage()
        }
    }
}