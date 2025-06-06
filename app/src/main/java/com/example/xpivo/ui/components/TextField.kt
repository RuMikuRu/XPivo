package com.example.xpivo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xpivo.ui.theme.MediumStyle
import com.example.xpivo.ui.theme.PrimaryBeige
import com.example.xpivo.ui.theme.PrimaryBrown
import com.example.xpivo.ui.theme.RegularStyle
import com.example.xpivo.ui.theme.SimpleShape

@Composable
fun PrimaryBasicTextField(
    value: String,
    title: String? = null,
    enabled: Boolean = true,
    placeholder: String = "",
    textModifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        if (title != null) {
            Text(text = title, style = MediumStyle)
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder, style = RegularStyle)
            },
            modifier = textModifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = PrimaryBeige,
                focusedContainerColor = PrimaryBeige,
                focusedPlaceholderColor = PrimaryBrown,
                unfocusedPlaceholderColor = PrimaryBrown,
                focusedTextColor = PrimaryBrown,
                unfocusedTextColor = PrimaryBrown,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = PrimaryBrown,
                disabledTextColor = PrimaryBrown,
                disabledPlaceholderColor = PrimaryBrown,
                disabledContainerColor = PrimaryBeige,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = SimpleShape,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            enabled = enabled
        )
    }
}

@Composable
fun PrimaryBigTextField(value: String, onValueChange: (String) -> Unit) {
    PrimaryBasicTextField(
        value = value,
        onValueChange = onValueChange,
        textModifier = Modifier.height(144.dp)
    )
}

@Composable
@Preview
fun PreviewPrimaryBigTextField() {
    var value by remember { mutableStateOf("") }
    PrimaryBigTextField(value = value, onValueChange = {it -> value = it})
}

@Composable
fun PrimaryPasswordTextField(
    value: String,
    placeholder: String = "",
    title: String? = null,
    onValueChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        if (title != null) {
            Text(text = title, style = MediumStyle)
        }
        PrimaryBasicTextField(
            value = value,
            placeholder = placeholder,
            onValueChange = onValueChange,
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Composable
@Preview
private fun PreviewBasicPrimaryTextField() {
    var value by remember { mutableStateOf("") }

    PrimaryBasicTextField(
        value = value,
        placeholder = "Имя пользователя",
        onValueChange = { it -> value = it })
}

@Composable
@Preview
private fun PreviewPasswordTextField() {
    var value by remember { mutableStateOf("") }
    PrimaryPasswordTextField(value = value, placeholder = "Имя пользователя") { it -> value = it }
}