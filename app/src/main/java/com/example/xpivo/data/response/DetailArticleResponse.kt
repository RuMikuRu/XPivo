package com.example.xpivo.data.response

import com.example.xpivo.data.model.ArticleStatus
import com.google.gson.annotations.SerializedName

data class DetailArticleResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("body")
    val body: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("images")
    val images: List<String> = emptyList(),

    @SerializedName("authorUsername")
    val authorUsername: String,

    @SerializedName("tags")
    val tags: List<String> = emptyList()
) {
    fun getStatus(): ArticleStatus {
        when (this.status) {
            "черновик" -> return ArticleStatus.Draft
            "опубликовано" -> return ArticleStatus.Published
            "На рассмотрении" -> return ArticleStatus.Review
        }
        return ArticleStatus.Draft
    }
}