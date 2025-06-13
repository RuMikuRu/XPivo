package com.example.xpivo.feature.create_article_page

import android.content.Context
import com.example.xpivo.core.view_model.BaseViewModel
import com.example.xpivo.data.repository.article_repository.IArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateArticleViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val repository: IArticlesRepository
) : BaseViewModel(context) {

    private val _articleTitle = MutableStateFlow("")
    val articleTitle = _articleTitle.asStateFlow()

    private val _articleText = MutableStateFlow("")
    val articleText = _articleText.asStateFlow()

    fun updateArticleTitle(title: String) {
        _articleTitle.value = title
    }

    fun updateArticleText(text: String) {
        _articleText.value = text
    }

    fun createArticle() {

    }

}
