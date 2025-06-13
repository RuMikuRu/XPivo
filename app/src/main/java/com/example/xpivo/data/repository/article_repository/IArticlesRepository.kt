package com.example.xpivo.data.repository.article_repository

import com.example.xpivo.data.response.Article
import com.example.xpivo.data.response.DetailArticleResponse

interface IArticlesRepository {
    suspend fun getArticles() : List<Article>
    suspend fun getDetailArticle(id: Int) : DetailArticleResponse
    suspend fun getArticlesByUserId():List<DetailArticleResponse>
    suspend fun createArticle(title: String, body: String, status:String, description: String, tags: List<String>)
}