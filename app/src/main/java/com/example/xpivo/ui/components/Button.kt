package com.example.xpivo.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun PrimaryFilterButton(title: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = { onClick.invoke() },
        shape = SimpleShape,
        enabled = !selected,
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBeige,
            disabledContainerColor = PrimaryBrown
        )
    ) {
        Text(text = title, style = TitleFilterStyle)
    }
}

@Composable
@Preview
private fun PreviewSelectedPrimaryFilterButton() {
    PrimaryFilterButton(title = "Автор", selected = true) { }
}

@Composable
@Preview
private fun PreviewUnSelectedPrimaryFilterButton() {
    PrimaryFilterButton(title = "Автор", selected = false) { }
}


@Preview
@Composable
private fun PreviewPrimaryButton() {
    PrimaryButton(title = "Войти") {

    }
}