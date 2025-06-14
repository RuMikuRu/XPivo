package com.example.xpivo.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.xpivo.R
import com.example.xpivo.ui.theme.PrimaryBeige
import com.example.xpivo.ui.theme.PrimaryBrown
import com.example.xpivo.ui.theme.PrimaryWhite
import com.example.xpivo.ui.theme.SimpleShape
import com.example.xpivo.ui.theme.TitleFilterStyle

@Composable
fun PrimaryCheckBox(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Checkbox(
        checked = checked, onCheckedChange = onCheckedChange, colors = CheckboxDefaults.colors(
            checkmarkColor = PrimaryBrown,
            checkedColor = PrimaryBeige,
            uncheckedColor = PrimaryBeige
        )
    )
}

@Composable
fun ImageProfile(image: ImageBitmap? = null, onClick: () -> Unit) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
        .clickable {
            onClick.invoke()
        }
    if (image == null) {
        Image(
            painter = painterResource(R.drawable.image_profile_placeholder),
            modifier = modifier,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    } else {
        Image(bitmap = image, contentDescription = null, modifier = Modifier.clickable {onClick.invoke()})
    }
}

@Composable
fun ImageArticle(image: ImageBitmap? = null) {
    if (image == null){
        Image(
            painter = painterResource(R.drawable.image_profile_placeholder),
            modifier = Modifier.fillMaxWidth().height(320.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    } else {
        Image(
            bitmap = image,
            modifier = Modifier.fillMaxWidth().height(320.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun SmallImage(image: ImageBitmap? = null) {
    if (image == null) {
        Image(
            painter = painterResource(R.drawable.image_profile_placeholder),
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    } else {
        Image(
            bitmap = image,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun PrimaryDropDownMenu(
    titleContent: Map<Int, String>,
    onValueChange: (key: Int, value: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf("") }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        PrimaryBasicTextField(
            value = selectedValue,
            onValueChange = {},
            enabled = false,
            placeholder = "Выберите пол",
            title = "Пол",
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        painter = painterResource(R.drawable.selector_dropdown_menu),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            repeat(titleContent.size) { pos ->
                DropdownMenuItem(
                    text = { Text(text = titleContent[pos].toString()) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PrimaryBeige),
                    colors = MenuDefaults.itemColors(textColor = PrimaryBrown),
                    onClick = {
                        selectedValue = titleContent[pos].toString()
                        expanded = false
                        onValueChange.invoke(pos, titleContent[pos].toString())
                    })
            }
        }
    }
}

@Composable
fun PrimaryTag(text: String) {
    Box(
        modifier = Modifier
            .background(color = PrimaryBeige, shape = SimpleShape)
            .widthIn(min = 99.dp).height(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = TitleFilterStyle, modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
@Preview
private fun PreviewPrimaryTag() {
    PrimaryTag(text = "Пиво")
}

@Composable
@Preview
private fun PreviewDropDownMenu() {
    val content = mapOf<Int, String>(0 to "Мужской", 1 to "Женский")
    PrimaryDropDownMenu(content, onValueChange = { it, it1 -> })
}

@Composable
@Preview
private fun PreviewImageProfile() {
    ImageProfile() { }
}

@Preview
@Composable
private fun PreviewPrimaryCheckBox() {
    var checked by remember { mutableStateOf(false) }
    PrimaryCheckBox(checked = checked, onCheckedChange = { it -> checked = it })
}