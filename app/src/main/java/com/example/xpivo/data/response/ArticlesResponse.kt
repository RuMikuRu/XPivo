package com.example.xpivo.data.response

import com.example.xpivo.data.request.CreateArticleRequest
import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String? = "",

    @SerializedName("author")
    val author: Author? = null,

    @SerializedName("tags")
    val tags: List<String> = emptyList(),

    @SerializedName("images")
    val images: List<Image> = emptyList(),

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("publishedAt")
    val publishedAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?
)

data class Author(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("username")
    val username: String?
)

data class Image(
    @SerializedName("id")
    val id: Int?
)