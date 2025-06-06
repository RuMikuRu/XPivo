package com.example.xpivo.feature.create_article_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.xpivo.ui.components.ActionRowLayout
import com.example.xpivo.ui.components.PrimaryBasicTextField
import com.example.xpivo.ui.components.PrimaryBigTextField
import com.example.xpivo.ui.components.PrimaryButton
import com.example.xpivo.ui.theme.PrimaryBeige

@Composable
fun CreateArticlePage() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            PrimaryBasicTextField(
                value = "",
                onValueChange = {},
                placeholder = "Название статьи (макс. 100 символов)"
            )
            PrimaryBigTextField(value = "") { }
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