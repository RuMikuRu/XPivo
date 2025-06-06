package com.example.xpivo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xpivo.R
import com.example.xpivo.ui.theme.BoldStyle
import com.example.xpivo.ui.theme.MediumStyle
import com.example.xpivo.ui.theme.PrimaryWhite
import com.example.xpivo.ui.theme.SimpleShape
import com.example.xpivo.ui.theme.SmallTextStyle

@Composable
fun PrimaryMiniArticleCard(title: String, dateTime: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.pivo_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp, 56.dp)
                .clip(SimpleShape)
        )
        TextContentMiniCard(title = title, dateTime = dateTime)
    }
}

@Composable
private fun TextContentMiniCard(title: String, dateTime: String) {
    Column {
        Text(text = title, style = MediumStyle)
        Text(text = dateTime, style = SmallTextStyle)
    }
}

@Composable
fun PrimaryBigArticleCard(status: String, title: String, description: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = PrimaryWhite),
        shape = SimpleShape,
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            TextContentBigCard(
                modifier = Modifier.weight(1f),
                status = status,
                title = title,
                description = description
            )
            Image(
                painter = painterResource(R.drawable.pivo_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(119.dp, 216.dp)
                    .clip(SimpleShape)
            )
        }
    }
}

@Composable
fun TextContentBigCard(
    modifier: Modifier = Modifier,
    status: String,
    title: String,
    description: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = modifier) {
        Text(text = status, style = SmallTextStyle)
        Text(text = title, style = BoldStyle)
        Text(text = description, style = SmallTextStyle)
    }
}

@Composable
@Preview
private fun PreviewPrimaryMiniArticleCard() {
    PrimaryMiniArticleCard(title = "Crafting the Perfect IPA", dateTime = "Published on 2023-08-15")
}

@Composable
@Preview
private fun PreviewPrimaryBigArticleCard() {
    PrimaryBigArticleCard(
        status = "Submitted",
        title = "Crafting the Perfect Stout: A Brewer's Guide",
        description = "Dive into the art of brewing a rich, flavorful stout. From selecting the right malts to mastering the fermentation process, this guide covers everything you need to know to create a stout that stands out."
    )
}