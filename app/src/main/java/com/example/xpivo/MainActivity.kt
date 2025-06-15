package com.example.xpivo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.xpivo.navigation.PrimaryNavHost
import com.example.xpivo.navigation.Screen
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xpivo.ui.components.PrimaryNavBar
import com.example.xpivo.ui.components.PrimaryTopBar
import com.example.xpivo.ui.components.SelectedMenuNavBar
import com.example.xpivo.ui.components.TypeBar
import com.example.xpivo.ui.theme.PrimaryWhite
import com.example.xpivo.ui.theme.XPivoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val showBottomBar = currentRoute != Screen.LoginPage.route &&
                    currentRoute != Screen.RegistrationPage.route && currentRoute != Screen.CreateArticlePage.route
            val viewModel: MainViewModel = hiltViewModel()

            val logoutState by viewModel.logoutResult.collectAsState(initial = null)
            val authError by viewModel.unauthorizedEvent.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.logoutResult.collect { success ->
                    if (success == true) {
                        navController.navigate(Screen.LoginPage.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            }

            LaunchedEffect(authError) {
                if (authError == true) {
                    navController.navigate(Screen.LoginPage.route) {
                        popUpTo(0) { inclusive = true }
                    }
                    viewModel.onUnauthorizedEventHandled()
                }
            }

            XPivoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = PrimaryWhite,
                    topBar = {
                        val topBarType = when (currentRoute) {
                            Screen.ArticlesPage.route -> TypeBar.ADD
                            Screen.ProfilePage.route -> TypeBar.EXIT
                            Screen.UserArticlePage.route -> TypeBar.SIMPLE
                            Screen.DetailArticlePage.route -> TypeBar.SIMPLE
                            else -> null
                        }
                        val topBarTitle = when (currentRoute) {
                            Screen.ArticlesPage.route -> "Статьи"
                            Screen.ProfilePage.route -> "Профиль"
                            Screen.UserArticlePage.route -> "Пользовательские статьи"
                            Screen.DetailArticlePage.route -> "Статья"
                            else -> null
                        }

                        if (topBarType != null && topBarTitle != null) {
                            PrimaryTopBar(
                                title = topBarTitle,
                                type = topBarType,
                                onClickExit = {
                                    viewModel.logout()
                                },
                                onClickAddArticle = {
                                    navController.navigate(Screen.CreateArticlePage.route) {
                                        popUpTo(Screen.CreateArticlePage.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                    },
                    bottomBar = {
                        if (showBottomBar) {
                            PrimaryNavBar(
                                startSelected = when (currentRoute) {
                                    Screen.ArticlesPage.route -> SelectedMenuNavBar.ARTICLES
                                    else -> SelectedMenuNavBar.NONE
                                },
                                onClickArticles = {
                                    navController.navigate(Screen.ArticlesPage.route) {
                                        popUpTo(Screen.ArticlesPage.route) { inclusive = true }
                                    }
                                },
                                onClickAddArticle = {
                                    navController.navigate(Screen.UserArticlePage.route) {
                                        popUpTo(Screen.UserArticlePage.route) { inclusive = true }
                                    }
                                },
                                onClickProfile = {
                                    navController.navigate(Screen.ProfilePage.route) {
                                        popUpTo(Screen.ProfilePage.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        )
                    ) {
                        PrimaryNavHost(navController = navController, authError = authError)
                    }
                }
            }

        }
    }
}