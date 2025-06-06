package com.example.xpivo.feature.articles_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.xpivo.R
import com.example.xpivo.data.model.Article
import com.example.xpivo.data.model.ArticleStatus
import com.example.xpivo.ui.components.PrimaryBasicTextField
import com.example.xpivo.ui.components.PrimaryFilterChip
import com.example.xpivo.ui.components.PrimaryMiniArticleCard

@Composable
fun ArticlesPage(mockData: List<Article> = listOf()) {
    LazyColumn {
        item {
            PrimaryBasicTextField(
                value = "",
                onValueChange = {},
                placeholder = "Поиск статей",
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                })
        }
        items(items = mockData) { article ->
            PrimaryMiniArticleCard(title = article.title, dateTime = article.createdAt)
        }
    }
}

@Composable
@Preview
private fun PreviewArticlesPage() {
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
    ArticlesPage(mockData)
}