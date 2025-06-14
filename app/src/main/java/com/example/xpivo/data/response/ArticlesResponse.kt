package com.example.xpivo.data.response

import com.example.xpivo.data.model.ArticleStatus
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
    val images: List<String> = emptyList(),

    @SerializedName("status")
    val status: String,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("publishedAt")
    val publishedAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?
) {
    fun getStatus(): ArticleStatus {
        return when (this.status) {
            "черновик" -> return ArticleStatus.Draft
            "опубликована" -> return ArticleStatus.Published
            "На рассмотрении" -> return ArticleStatus.Review
            else -> {return ArticleStatus.Draft}
        }
    }
}

data class Author(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("username")
    val username: String?
)