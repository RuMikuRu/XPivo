package com.example.xpivo.feature.create_article_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xpivo.ui.components.ActionRowLayout
import com.example.xpivo.ui.components.PrimaryBasicTextField
import com.example.xpivo.ui.components.PrimaryBigTextField
import com.example.xpivo.ui.components.PrimaryButton
import com.example.xpivo.ui.theme.PrimaryBeige

@Composable
fun CreateArticlePage(viewModel: CreateArticleViewModel = hiltViewModel()) {

    val titleArticle by viewModel.articleTitle.collectAsState()
    val textArticle by viewModel.articleText.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            PrimaryBasicTextField(
                value = titleArticle,
                onValueChange = {it -> viewModel.updateArticleTitle(it)},
                placeholder = "Название статьи (макс. 100 символов)"
            )
            PrimaryBigTextField(value = textArticle) {it -> viewModel.updateArticleText(it) }
        }
        ActionRowLayout {
            PrimaryButton(title = "Сохранить черновик", colorType = PrimaryBeige) { }
            PrimaryButton(title = "Опубликовать") { }
        }
    }
}

@Composable
@Preview
private fun PreviewCreateArticlePage() {
    CreateArticlePage()
}