package com.example.xpivo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xpivo.R
import com.example.xpivo.ui.theme.LargeStyle
import com.example.xpivo.ui.theme.PrimaryBlack
import com.example.xpivo.ui.theme.PrimaryBrown
import com.example.xpivo.ui.theme.PrimaryWhite
import com.example.xpivo.ui.theme.TitleNavStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTopBar(title: String, type: TypeBar) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = LargeStyle,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            if (type == TypeBar.EXIT) {
                Icon(
                    painter = painterResource(R.drawable.ic_exit),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 16.dp)
                )
            } else if (type == TypeBar.SIMPLE) {
                Icon(
                    painter = painterResource(R.drawable.ic_back_arrow),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        },
        actions = {
            if (type == TypeBar.ADD) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

enum class TypeBar {
    ADD,
    EXIT,
    SIMPLE
}

@Composable
fun PrimaryNavBar(
    startSelected: SelectedMenuNavBar,
    onClickArticles: () -> Unit,
    onClickAddArticle: () -> Unit,
    onClickProfile: () -> Unit
) {
    var selected by remember { mutableStateOf(startSelected) }
    NavigationBar(
        containerColor = PrimaryWhite
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = {
                onClickArticles.invoke()
                selected = SelectedMenuNavBar.ARTICLES
            }, modifier = Modifier.weight(1f)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_articles),
                        tint = if (selected == SelectedMenuNavBar.ARTICLES) PrimaryBrown else PrimaryBlack,
                        contentDescription = null
                    )
                    Text(
                        text = "Статьи",
                        style = TitleNavStyle,
                        color = if (selected == SelectedMenuNavBar.ARTICLES) PrimaryBrown else PrimaryBlack
                    )
                }
            }
            IconButton(onClick = {
                onClickAddArticle.invoke()
                selected = SelectedMenuNavBar.ADD_ARTICLE
            }, modifier = Modifier.weight(1f)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_article),
                        tint = if (selected == SelectedMenuNavBar.ADD_ARTICLE) PrimaryBrown else PrimaryBlack,
                        contentDescription = null
                    )
                    Text(
                        text = "Создать",
                        style = TitleNavStyle,
                        color = if (selected == SelectedMenuNavBar.ADD_ARTICLE) PrimaryBrown else PrimaryBlack,
                    )
                }
            }
            IconButton(onClick = {
                onClickProfile.invoke()
                selected = SelectedMenuNavBar.PROFILE
            }, modifier = Modifier.weight(1f)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_profile),
                        tint = if (selected == SelectedMenuNavBar.PROFILE) PrimaryBrown else PrimaryBlack,
                        contentDescription = null
                    )
                    Text(
                        text = "Профиль",
                        style = TitleNavStyle,
                        color = if (selected == SelectedMenuNavBar.PROFILE) PrimaryBrown else PrimaryBlack,
                    )
                }
            }
        }
    }
}

enum class SelectedMenuNavBar {
    ARTICLES,
    ADD_ARTICLE,
    PROFILE,
    NONE
}

@Composable
@Preview(group = "TopBar")
private fun PreviewADDPrimaryTopBar() {
    PrimaryTopBar(title = "Статья", type = TypeBar.ADD)
}

@Composable
@Preview(group = "TopBar")
private fun PreviewEXITPrimaryTopBar() {
    PrimaryTopBar(title = "Статья", type = TypeBar.EXIT)
}

@Composable
@Preview(group = "TopBar")
private fun PreviewSIMPLEPrimaryTopBar() {
    PrimaryTopBar(title = "Статья", type = TypeBar.SIMPLE)
}

@Composable
@Preview(group = "NavBar")
private fun PreviewNavBar() {
    PrimaryNavBar(startSelected = SelectedMenuNavBar.ARTICLES, {}, {}, {})
}