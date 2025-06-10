package com.example.xpivo.feature.user_article_page

import android.content.Context
import com.example.xpivo.core.view_model.BaseViewModel
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.repository.article_repository.IArticlesRepository
import com.example.xpivo.data.response.DetailArticleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserArticlesViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: IArticlesRepository
) : BaseViewModel(context) {
    private val _articles = MutableStateFlow<Lce<List<DetailArticleResponse>>>(Lce.Ready(listOf()))
    val articles = _articles.asStateFlow()

    init {
        loadArticles()
    }

    private fun loadArticles() {
        launchSafely {
            _articles.value = Lce.Loading

            try {
                val articles = repository.getArticlesByUserId()
                _articles.value = Lce.Content(articles)
            } catch (e: Exception) {
                _articles.value = Lce.Error(e)
                sendError(e)
            }
        }
    }
}