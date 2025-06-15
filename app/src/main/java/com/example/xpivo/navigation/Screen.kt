package com.example.xpivo.navigation

sealed class Screen(val route: String) {
    object LoginPage : Screen("login")
    object RegistrationPage : Screen("registration")
    object ArticlesPage : Screen("articles")
    object DetailArticlePage : Screen("detailArticle/{id}/{status}") {
        fun createRoute(id: Int, status: Boolean) = "detailArticle/$id/$status"
    }
    object CreateArticlePage : Screen("createArticlePage?articleId={articleId}") {
        fun createRoute(articleId: Int?) = "createArticlePage?articleId=${articleId ?: -1}"
    }
    object UserArticlePage : Screen("userArticlePage")
    object ProfilePage : Screen("profile")
}
