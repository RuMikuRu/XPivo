package com.example.xpivo.data.repository.article_repository

import com.example.xpivo.data.response.Article

interface IArticlesRepository {
    suspend fun getArticles() : List<Article>
}