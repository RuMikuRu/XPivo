package com.example.xpivo.feature.articles_page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xpivo.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.model.ArticleStatus
import com.example.xpivo.data.response.Article
import com.example.xpivo.ui.components.PrimaryBasicTextField
import com.example.xpivo.ui.components.PrimaryFilterChip
import com.example.xpivo.ui.components.PrimaryMiniArticleCard

@Composable
fun ArticlesPage(viewModel: ArticlesViewModel = hiltViewModel()) {
    val viewState by viewModel.articlesState.collectAsState()
    when (viewState) {
        is Lce.Content<List<Article>> -> {
            val articles = (viewState as Lce.Content<List<Article>>).data
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
                items(items = articles) { article ->
                    PrimaryMiniArticleCard(title = article.title ?: "", dateTime = article.createdAt ?: "")
                }
            }
        }

        is Lce.Error -> {
            Text(text = "Error")
        }

        Lce.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Lce.Ready<List<Article>> -> {

        }
    }

}

@Composable
@Preview
private fun PreviewArticlesPage() {
//    val mockData = listOf<Article>(
//        Article(
//            id = 0,
//            title = "title",
//            description = "description",
//            body = "body",
//            createdAt = "createdAt",
//            status = ArticleStatus.Draft,
//            images = listOf()
//        ),
//        Article(
//            id = 0,
//            title = "title",
//            description = "description",
//            body = "body",
//            createdAt = "createdAt",
//            status = ArticleStatus.Published,
//            images = listOf()
//        ),
//        Article(
//            id = 0,
//            title = "title",
//            description = "description",
//            body = "body",
//            createdAt = "createdAt",
//            status = ArticleStatus.Review,
//            images = listOf()
//        ),
//    )
//    ArticlesPage(mockData)
}