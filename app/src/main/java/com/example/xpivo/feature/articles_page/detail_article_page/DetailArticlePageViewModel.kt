package com.example.xpivo.feature.articles_page.detail_article_page

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
class DetailArticlePageViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: IArticlesRepository
) : BaseViewModel(context) {
    private val _detailArticleState = MutableStateFlow<Lce<DetailArticleResponse?>>(Lce.Ready(null))
    val detailArticleState = _detailArticleState.asStateFlow()

    fun loadDetailArticle(id: Int) {
        launchSafely {
            _detailArticleState.value = Lce.Loading
            try {
                val detailArticle = repository.getDetailArticle(id)
                _detailArticleState.value = Lce.Content(detailArticle)
            } catch (e: Exception) {
                _detailArticleState.value = Lce.Error(e)
                sendError(e)
            }
        }
    }
}