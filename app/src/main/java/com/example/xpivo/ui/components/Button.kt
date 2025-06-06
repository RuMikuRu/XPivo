package com.example.xpivo.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.xpivo.ui.theme.BoldStyle
import com.example.xpivo.ui.theme.PrimaryBeige
import com.example.xpivo.ui.theme.PrimaryBrown
import com.example.xpivo.ui.theme.PrimaryOrange
import com.example.xpivo.ui.theme.SimpleShape
import com.example.xpivo.ui.theme.TitleFilterStyle

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    title: String,
    colorType: Color = PrimaryOrange,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick.invoke() },
        shape = SimpleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorType,
            contentColor = Color.Black
        ),
        modifier = modifier
    ) {
        Text(text = title, style = BoldStyle)
    }
}

@Composable
fun PrimaryFilterChip(
    title: String,
    selected: Boolean,
    onClick: (Boolean) -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = { onClick(!selected) }, // <- Переключение состояния
        label = {
            Text(text = title, style = TitleFilterStyle)
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = PrimaryBeige,
            selectedContainerColor = PrimaryBrown
        ),
        shape = SimpleShape,
        border = null
    )
}

@Composable
@Preview
private fun PreviewSelectedPrimaryFilterButton() {
    var selected by remember { mutableStateOf(false) }

    PrimaryFilterChip(
        title = "Фильтр",
        selected = selected,
        onClick = { selected = it }
    )
}


@Preview
@Composable
private fun PreviewPrimaryButton() {
    PrimaryButton(title = "Войти") {

    }
}