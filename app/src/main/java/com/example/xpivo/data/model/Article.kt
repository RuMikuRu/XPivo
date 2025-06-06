package com.example.xpivo.data.model

import androidx.compose.ui.graphics.ImageBitmap

data class Article(
    val id:Int,
    val title:String,
    val description:String,
    val body:String,
    val createdAt:String,
    val status: ArticleStatus,
    val images: List<ImageBitmap>,
)

enum class ArticleStatus(value: String) {
    Draft("черновик"),
    Published("опубликовано"),
    Review("На рассмотрении")
}