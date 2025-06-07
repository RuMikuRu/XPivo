package com.example.xpivo.navigation

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xpivo.data.cache.DataStoreCache
import com.example.xpivo.feature.articles_page.ArticlesPage
import com.example.xpivo.feature.login_page.LoginPage
import androidx.compose.runtime.getValue
import com.example.xpivo.feature.registration_page.RegistrationPage
import kotlinx.coroutines.flow.first

@Composable
fun PrimaryNavHost(
    viewModel: NavigationViewModel = hiltViewModel<NavigationViewModel>(),
    navController: NavHostController = rememberNavController(),
) {
    val authToken by viewModel.authToken.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val startDestination = if (authToken.isNotEmpty()) {
        Screen.RegistrationPage.route
    } else {
        Screen.LoginPage.route
    }
    if (uiState) {
        NavHost(navController, startDestination = startDestination) {
            composable(Screen.LoginPage.route) {
                LoginPage(
                    navController = navController,
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
}