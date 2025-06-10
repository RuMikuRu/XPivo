package com.example.xpivo.data.repository.article_repository

import android.util.Log
import com.example.xpivo.data.cache.DataStoreCache
import com.example.xpivo.data.cache.FreeCache
import com.example.xpivo.data.response.Article
import com.example.xpivo.data.response.DetailArticleResponse
import com.example.xpivo.network.Service
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val service: Service,
    private val dataStoreCache: DataStoreCache
) :
    IArticlesRepository {
    override suspend fun getArticles(): List<Article> {
        Log.d("ArticlesRepositoryImpl", "getArticles: ${service.getArticles()}")
        return service.getArticles()
    }

    override suspend fun getDetailArticle(id: Int): DetailArticleResponse {
        return service.getDetailArticle(id)
    }

    override suspend fun getArticlesByUserId(): List<DetailArticleResponse> {
        val id = dataStoreCache.userId.first() ?: FreeCache.userId
        id?.let {
            return service.getArticleByUserId(id)
        }
        return listOf()
    }
}