package com.example.xpivo.feature.articles_page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xpivo.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.model.ArticleStatus
import com.example.xpivo.data.response.Article
import com.example.xpivo.navigation.Screen
import com.example.xpivo.ui.components.PrimaryBasicTextField
import com.example.xpivo.ui.components.PrimaryFilterChip
import com.example.xpivo.ui.components.PrimaryMiniArticleCard

@Composable
fun ArticlesPage(navController: NavController, viewModel: ArticlesViewModel = hiltViewModel()) {
    val viewState by viewModel.articlesState.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }
    var titleFilter by remember { mutableStateOf("Новое") }
    var filterSelected by remember { mutableStateOf(false) }
    LazyColumn {
        item {
            PrimaryBasicTextField(
                value = query,
                onValueChange = { it ->
                    query = it
                    viewModel.searchArticles(query)
                },
                placeholder = "Поиск статей",
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                })
        }
        item {
            PrimaryFilterChip(title = titleFilter, selected = filterSelected) {
                filterSelected = !filterSelected
                if(filterSelected) {
                    titleFilter = "Новое"
                } else {
                    titleFilter = "Старое"
                }
                viewModel.filterList()
            }
        }
        when (viewState) {
            is Lce.Content<List<Article>> -> {
                val articles = (viewState as Lce.Content<List<Article>>).data
                items(
                    items = articles.filter { article -> article.getStatus() == ArticleStatus.Published },
                    key = { article -> article.id }) { article ->
                    PrimaryMiniArticleCard(
                        title = article.title ?: "",
                        dateTime = article.createdAt ?: "",
                        image = if (article.images.isNotEmpty()) article.images[0] else null
                    ) {
                        navController.navigate(
                            Screen.DetailArticlePage.createRoute(
                                article.id,
                                false
                            )
                        )
                    }
                }
            }

            is Lce.Error -> {
                item {
                    Text(text = "Error")
                }
            }

            Lce.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is Lce.Ready<List<Article>> -> {

            }
        }
    }
}

@Composable
@Preview
private fun PreviewArticlesPage() {
}