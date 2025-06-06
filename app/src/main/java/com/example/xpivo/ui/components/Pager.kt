package com.example.xpivo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xpivo.ui.theme.PrimaryBeige
import com.example.xpivo.ui.theme.PrimaryBlack
import com.example.xpivo.ui.theme.PrimaryBrown
import com.example.xpivo.ui.theme.PrimaryWhite
import com.example.xpivo.ui.theme.SimpleShape
import com.example.xpivo.ui.theme.TitleFilterStyle

@Composable
fun CustomTabLayout(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("Опубликовано и отправлено", "Черновик")
    Box(modifier = Modifier.padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryBeige, shape = SimpleShape)
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = index == selectedTab
                val backgroundColor = if (isSelected) PrimaryWhite else PrimaryBeige
                val textColor = if (isSelected) PrimaryBlack else PrimaryBrown

                Box(
                    modifier = Modifier
                        .weight(if (index == 0) 1f else 0.5f)
                        .clip(SimpleShape)
                        .background(backgroundColor)
                        .clickable { onTabSelected(index) }
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = textColor,
                        style = TitleFilterStyle
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewTabLayout() {
    var selectedTab by remember { mutableIntStateOf(0) }
    CustomTabLayout(selectedTab = selectedTab) {it -> selectedTab = it }
}