package com.example.xpivo.feature.user_article_page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.xpivo.data.model.Article
import com.example.xpivo.data.model.ArticleStatus
import com.example.xpivo.ui.components.CustomTabLayout
import com.example.xpivo.ui.components.PrimaryBigArticleCard
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier

@Composable
fun UserArticlePage(mockData: List<Article> = listOf()) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            CustomTabLayout(selectedTab = selectedTab) { selectedTab = it }
        }
        when (selectedTab) {
            0 -> items(items = mockData.filter { article -> article.status != ArticleStatus.Draft }) { article ->
                PrimaryBigArticleCard(
                    status = article.status.name,
                    title = article.title,
                    description = article.description
                )
            }

            1 -> items(items = mockData.filter { article -> article.status == ArticleStatus.Draft }) { article ->
                PrimaryBigArticleCard(
                    status = article.status.name,
                    title = article.title,
                    description = article.description
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewUserArticlePage() {
    val mockData = listOf<Article>(
        Article(
            id = 0,
            title = "title",
            description = "description",
            body = "body",
            createdAt = "createdAt",
            status = ArticleStatus.Draft,
            images = listOf()
        ),
        Article(
            id = 0,
            title = "title",
            description = "description",
            body = "body",
            createdAt = "createdAt",
            status = ArticleStatus.Published,
            images = listOf()
        ),
        Article(
            id = 0,
            title = "title",
            description = "description",
            body = "body",
            createdAt = "createdAt",
            status = ArticleStatus.Review,
            images = listOf()
        ),
    )
    UserArticlePage(mockData)
}