package com.example.xpivo.feature.articles_page.detail_article_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.response.DetailArticleResponse
import com.example.xpivo.ui.components.ImageArticle
import com.example.xpivo.ui.components.ImageProfile
import com.example.xpivo.ui.components.PrimaryTag
import com.example.xpivo.ui.theme.ArticleStyle
import com.example.xpivo.ui.theme.SmallTextStyle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailArticlePage(idArticle: Int, viewModel: DetailArticlePageViewModel = hiltViewModel()) {
    val detailArticleState by viewModel.detailArticleState.collectAsState()
    viewModel.loadDetailArticle(idArticle)
    when (detailArticleState) {
        is Lce.Content<DetailArticleResponse?> -> {
            val detailArticle = (detailArticleState as Lce.Content<DetailArticleResponse?>).data
            detailArticle?.let { article ->
                Column {
                    ImageArticle()
                    Text(
                        text = article.title,
                        style = ArticleStyle,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    Text(
                        text = "Публикация от  ${article.createdAt}, автор ${article.authorUsername}",
                        style = SmallTextStyle,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        repeat(article.tags.size) { pos ->
                            PrimaryTag(article.tags[pos])
                        }
                    }
                    Text(
                        text = "In the heart of our brewery, we blend tradition with innovation to create beers that tell a story. From selecting the finest ingredients to the meticulous brewing process, every step is a testament to our commitment to quality. Our brewers, artisans in their own right, experiment with flavors and techniques, pushing boundaries while honoring the rich heritage of brewing. This dedication results in a diverse range of beers, each with its unique character and taste profile, inviting you to explore the depths of flavor and craftsmanship.",
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
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

        is Lce.Ready<DetailArticleResponse?> -> {

        }
    }

}

@Composable
@Preview
fun PreviewDetailArticlePage() {
    val tagList = listOf<String>("Пиво", "Крафтовое пиво", "Инновации", "Водка")
    DetailArticlePage(10)
}