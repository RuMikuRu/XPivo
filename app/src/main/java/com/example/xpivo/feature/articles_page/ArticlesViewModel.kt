package com.example.xpivo.feature.articles_page

import android.content.Context
import com.example.xpivo.core.view_model.BaseViewModel
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.repository.article_repository.IArticlesRepository
import com.example.xpivo.data.response.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val articlesRepository: IArticlesRepository
) : BaseViewModel(context) {
    private val _articlesState = MutableStateFlow<Lce<List<Article>>>(Lce.Ready(emptyList()))
    val articlesState: StateFlow<Lce<List<Article>>> = _articlesState.asStateFlow()

    init {
        loadArticles()
    }

    fun loadArticles() {
        launchSafely {
            _articlesState.value = Lce.Loading
            try {
                val articles = articlesRepository.getArticles()
                _articlesState.value = Lce.Content(articles)
            } catch (e: Exception) {
                _articlesState.value = Lce.Error(e)
                sendError(e)
            }
        }
    }

    fun searchArticles(query: String) {
        launchSafely {
            _articlesState.value = Lce.Loading
            try {
                val articles = articlesRepository.getArticles()
                    .filter { it.title?.contains(query, ignoreCase = true) == true }
                _articlesState.value = Lce.Content(articles)
            } catch (e: Exception) {
                _articlesState.value = Lce.Error(e)
                sendError(e)
            }
        }
    }

    fun filterList() {
        launchSafely {
            _articlesState.value = Lce.Loading
            try {
                val articles = articlesRepository.getArticles()
                    .reversed()
                _articlesState.value = Lce.Content(articles)
            } catch (e: Exception) {
                _articlesState.value = Lce.Error(e)
                sendError(e)
            }
        }
    }
}