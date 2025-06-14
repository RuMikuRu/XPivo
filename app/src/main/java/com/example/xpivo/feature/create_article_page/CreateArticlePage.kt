package com.example.xpivo.feature.create_article_page

import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xpivo.data.model.ArticleStatus
import com.example.xpivo.ui.components.ActionRowLayout
import com.example.xpivo.ui.components.ImageArticle
import com.example.xpivo.ui.components.PrimaryBasicTextField
import com.example.xpivo.ui.components.PrimaryBigTextField
import com.example.xpivo.ui.components.PrimaryButton
import com.example.xpivo.ui.components.SmallImage
import com.example.xpivo.ui.theme.PrimaryBeige
import com.example.xpivo.ui.theme.SmallTitleStyle

@Composable
fun CreateArticlePage(viewModel: CreateArticleViewModel = hiltViewModel()) {

    val titleArticle by viewModel.articleTitle.collectAsState()
    val textArticle by viewModel.articleText.collectAsState()
    val imageArticle by viewModel.articleImage.collectAsState()
    val activity = LocalActivity.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onImageSelected(it, activity!!)
        }
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            PrimaryBasicTextField(
                value = titleArticle,
                onValueChange = {it -> viewModel.updateArticleTitle(it)},
                placeholder = "Название статьи (макс. 100 символов)"
            )
            PrimaryBigTextField(value = textArticle) {it -> viewModel.updateArticleText(it) }
            Column {
                TextButton(onClick = {
                    imagePickerLauncher.launch("image/*")
                }) {
                    Text(text = "Добавить изображение", style = SmallTitleStyle)
                }
                SmallImage(imageArticle)
            }
        }
        ActionRowLayout {
            PrimaryButton(title = "Сохранить черновик", colorType = PrimaryBeige) {
                viewModel.createArticleDraft()
            }
            PrimaryButton(title = "Опубликовать") { viewModel.createArticlePublished()}
        }
    }
}

@Composable
@Preview
private fun PreviewCreateArticlePage() {
    CreateArticlePage()
}