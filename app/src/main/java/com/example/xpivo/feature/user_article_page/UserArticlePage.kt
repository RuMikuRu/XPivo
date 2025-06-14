package com.example.xpivo.feature.user_article_page

import android.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.xpivo.data.model.ArticleStatus
import com.example.xpivo.ui.components.CustomTabLayout
import com.example.xpivo.ui.components.PrimaryBigArticleCard
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.response.DetailArticleResponse
import com.example.xpivo.ui.theme.TitleStyle

@Composable
fun UserArticlePage(viewModel: UserArticlesViewModel = hiltViewModel()) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val articles by viewModel.articles.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        when (articles) {
            is Lce.Content<List<DetailArticleResponse>> -> {
                val articlesData = (articles as Lce.Content<List<DetailArticleResponse>>).data
                item {
                    CustomTabLayout(selectedTab = selectedTab) { selectedTab = it }
                }
                when (selectedTab) {
                    0 -> items(items = articlesData.filter { article -> article.getStatus() != ArticleStatus.Draft }) { article ->
                        PrimaryBigArticleCard(
                            status = article.status,
                            title = article.title,
                            description = article.description
                        )
                    }

                    1 -> items(items = articlesData.filter { article -> article.getStatus() == ArticleStatus.Draft }) { article ->
                        PrimaryBigArticleCard(
                            status = article.status,
                            title = article.title,
                            description = article.description
                        )
                    }
                }
            }

            is Lce.Error -> {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Тут пусто", style = TitleStyle)
                    }
                }
            }

            Lce.Loading -> {
                item {
                    CircularProgressIndicator()
                }
            }

            is Lce.Ready<*> -> {
                item {
                    CircularProgressIndicator()
                }
            }
        }
    }

}

@Composable
@Preview
private fun PreviewUserArticlePage() {
    UserArticlePage()
}