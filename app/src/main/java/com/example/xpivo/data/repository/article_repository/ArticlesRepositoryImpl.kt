package com.example.xpivo.data.repository.article_repository

import android.util.Log
import com.example.xpivo.data.response.Article
import com.example.xpivo.network.Service
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(private val service: Service) :
    IArticlesRepository {
    override suspend fun getArticles(): List<Article> {
        Log.d("ArticlesRepositoryImpl", "getArticles: ${service.getArticles()}")
        return service.getArticles()
    }
}