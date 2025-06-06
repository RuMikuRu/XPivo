package com.example.xpivo.navigation

sealed class Screen(val route: String) {
    object LoginPage : Screen("login")
    object RegistrationPage : Screen("registration")
    object ArticlesPage : Screen("articles")
    object CreateArticlePage : Screen("createArticlePage")
    object UserArticlePage : Screen("userArticlePage")
    object ProfilePage : Screen("profile")
}