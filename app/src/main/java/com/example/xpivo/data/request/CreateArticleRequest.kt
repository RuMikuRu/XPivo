package com.example.xpivo.data.request

import com.example.xpivo.data.model.ArticleStatus
import com.google.gson.annotations.SerializedName

data class CreateArticleRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagIds")
    val tagIds: List<Int> = listOf(),
    @SerializedName("images")
    val images: List<Byte> = listOf()
)
