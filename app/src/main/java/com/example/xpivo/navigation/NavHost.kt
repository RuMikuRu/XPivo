package com.example.xpivo.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.xpivo.feature.articles_page.ArticlesPage
import com.example.xpivo.feature.login_page.LoginPage
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.xpivo.feature.articles_page.detail_article_page.DetailArticlePage
import com.example.xpivo.feature.create_article_page.CreateArticlePage
import com.example.xpivo.feature.create_article_page.CreateArticleViewModel
import com.example.xpivo.feature.registration_page.RegistrationPage
import com.example.xpivo.feature.user_article_page.UserArticlePage
import com.example.xpivo.feature.user_profile_page.ProfilePage

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PrimaryNavHost(
    viewModel: NavigationViewModel = hiltViewModel<NavigationViewModel>(),
    navController: NavHostController,
    authError: Boolean?
) {
    val authToken by viewModel.authToken.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val startDestination = if (authToken.isNotEmpty() || authError == true) {
        Log.d("PrimaryNavHost", "PrimaryNavHost: $authToken")
        Screen.ArticlesPage.route
    } else {
        Screen.LoginPage.route
    }

    if (uiState) {
        NavHost(navController = navController, startDestination = startDestination) {
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
                RegistrationPage(navController)
            }
            composable(Screen.ArticlesPage.route) {
                ArticlesPage(navController)
            }
            composable(
                route = Screen.DetailArticlePage.route,
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("status") {
                        type =
                            NavType.BoolType
                    })
            ) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getInt("id") ?: return@composable
                val status = backStackEntry.arguments?.getBoolean("status") ?: return@composable
                DetailArticlePage(articleId, status)
            }

            composable(Screen.UserArticlePage.route) {
                UserArticlePage(navController = navController)
            }

            composable(Screen.ProfilePage.route) {
                ProfilePage(navController = navController)
            }

            composable(
                route = Screen.CreateArticlePage.route,
                arguments = listOf(
                    navArgument("articleId") {
                        type = NavType.IntType
                        defaultValue = -1
                        nullable = false
                    }
                )
            ) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getInt("articleId")
                val viewModel = hiltViewModel<CreateArticleViewModel>()

                LaunchedEffect(articleId) {
                    if (articleId != null && articleId != -1 && viewModel.editableArticle.value == null) {
                        viewModel.loadArticleForEdit(articleId)
                    }
                }

                CreateArticlePage(viewModel, navController)
            }
        }
    }
}