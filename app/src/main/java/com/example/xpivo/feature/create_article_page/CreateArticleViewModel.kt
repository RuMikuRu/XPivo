package com.example.xpivo.feature.create_article_page

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewModelScope
import com.example.xpivo.core.util.base64ToImageBitmap
import com.example.xpivo.core.util.imageBitmapToBase64
import com.example.xpivo.core.view_model.BaseViewModel
import com.example.xpivo.data.model.ArticleStatus
import com.example.xpivo.data.repository.article_repository.IArticlesRepository
import com.example.xpivo.data.response.DetailArticleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val _articleImage = MutableStateFlow<ImageBitmap?>(null)
    val articleImage = _articleImage.asStateFlow()

    private val _editableArticle = MutableStateFlow<DetailArticleResponse?>(null)
    val editableArticle = _editableArticle.asStateFlow()


    fun updateArticleTitle(title: String) {
        _articleTitle.value = title
    }

    fun updateArticleText(text: String) {
        _articleText.value = text
    }

    fun createArticlePublished() {
        launchSafely {
            try {
                if (_editableArticle.value == null) {
                    repository.createArticle(
                        title = _articleTitle.value,
                        body = _articleText.value,
                        description = _articleText.value,
                        status = ArticleStatus.Published.value,
                        tags = listOf(),
                        image = _articleImage.value?.let { imageBitmapToBase64(it) } ?: ""
                    )
                } else if (_editableArticle.value != null) {
                    repository.updateArticle(
                        id = _editableArticle.value!!.id,
                        title = _articleTitle.value,
                        body = _articleText.value,
                        description = _articleText.value,
                        status = ArticleStatus.Published.value,
                        tags = listOf(),
                        image = _articleImage.value?.let { imageBitmapToBase64(it) } ?: "")
                }
            } catch (e: Exception) {
                sendError(e)
            }
        }
    }

    fun loadArticleForEdit(id: Int) {
        viewModelScope.launch {
            try {
                val article = repository.getDetailArticle(id)
                _editableArticle.value = article
                _articleTitle.update { article.title }
                _articleText.update { article.body }
                _articleImage.update {
                    base64ToImageBitmap(article.images.firstOrNull() ?: "")
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }


    fun createArticleDraft() {
        launchSafely {
            try {
                if (_editableArticle.value == null) {
                    repository.createArticle(
                        title = _articleTitle.value,
                        body = _articleText.value,
                        description = _articleText.value,
                        status = ArticleStatus.Draft.value,
                        tags = listOf(),
                        image = _articleImage.value?.let { imageBitmapToBase64(it) } ?: ""
                    )
                } else if (_editableArticle.value != null) {
                    repository.updateArticle(
                        id = _editableArticle.value!!.id,
                        title = _articleTitle.value,
                        body = _articleText.value,
                        description = _articleText.value,
                        status = ArticleStatus.Draft.value,
                        tags = listOf(),
                        image = _articleImage.value?.let { imageBitmapToBase64(it) } ?: "")
                }
            } catch (e: Exception) {
                sendError(e)
            }
        }
    }

    fun onImageSelected(uri: Uri, context: Context) {
        viewModelScope.launch {
            try {
                val stream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(stream)
                _articleImage.value = bitmap.asImageBitmap()
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }
}
