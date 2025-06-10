package com.example.xpivo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.xpivo.navigation.PrimaryNavHost
import com.example.xpivo.navigation.Screen
import androidx.compose.runtime.getValue
import com.example.xpivo.ui.components.PrimaryNavBar
import com.example.xpivo.ui.components.SelectedMenuNavBar
import com.example.xpivo.ui.theme.PrimaryWhite
import com.example.xpivo.ui.theme.XPivoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val showBottomBar = currentRoute != Screen.LoginPage.route &&
                    currentRoute != Screen.RegistrationPage.route

            XPivoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = PrimaryWhite,
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
                                onClickAddArticle = { navController.navigate(Screen.UserArticlePage.route) {
                                    popUpTo(Screen.UserArticlePage.route) { inclusive = true }
                                } },
                                onClickProfile = { /* TODO */ }
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
                        PrimaryNavHost(navController = navController)
                    }
                }
            }
        }
    }
}