package com.example.xpivo.feature.articles_page.detail_article_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xpivo.ui.components.ImageArticle
import com.example.xpivo.ui.components.ImageProfile
import com.example.xpivo.ui.components.PrimaryTag
import com.example.xpivo.ui.theme.ArticleStyle
import com.example.xpivo.ui.theme.SmallTextStyle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailArticlePage(mockData: List<String> = listOf()) {
    Column {
        ImageArticle()
        Text(
            text = "Crafting Excellence: The Art of Brewing",
            style = ArticleStyle,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        Text(
            text = "Публикация от  15, 2023, автор Иван Иванов",
            style = SmallTextStyle,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(mockData.size) { pos ->
                PrimaryTag(mockData[pos])
            }
        }
        Text(
            text = "In the heart of our brewery, we blend tradition with innovation to create beers that tell a story. From selecting the finest ingredients to the meticulous brewing process, every step is a testament to our commitment to quality. Our brewers, artisans in their own right, experiment with flavors and techniques, pushing boundaries while honoring the rich heritage of brewing. This dedication results in a diverse range of beers, each with its unique character and taste profile, inviting you to explore the depths of flavor and craftsmanship.",
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

@Composable
@Preview
fun PreviewDetailArticlePage() {
    val tagList = listOf<String>("Пиво", "Крафтовое пиво", "Инновации", "Водка")
    DetailArticlePage(tagList)
}