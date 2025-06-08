package com.example.xpivo.core.di

import android.content.Context
import com.example.xpivo.core.util.NetworkState
import com.example.xpivo.data.cache.DataStoreCache
import com.example.xpivo.data.repository.article_repository.ArticlesRepositoryImpl
import com.example.xpivo.data.repository.article_repository.IArticlesRepository
import com.example.xpivo.data.repository.user_repository.IUserRepository
import com.example.xpivo.data.repository.user_repository.UserRepositoryImpl
import com.example.xpivo.network.ServerApi
import com.example.xpivo.network.ServerResponse
import com.example.xpivo.network.Service
import com.example.xpivo.network.interceptor.AuthInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        dataStoreCache: DataStoreCache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                AuthInterceptor(
                    tokenProvider = {
                        runBlocking {
                            dataStoreCache.tokenFlow.first()
                        }
                    }
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.8.1.11:5091/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ServerApi {
        return retrofit.create(ServerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkState(@ApplicationContext context: Context): NetworkState {
        return NetworkState(context)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideServerResponse(gson: Gson): ServerResponse {
        return ServerResponse(gson)
    }

    @Provides
    @Singleton
    fun provideService(
        serviceApi: ServerApi,
        networkState: NetworkState,
        serverResponse: ServerResponse
    ): Service {
        return Service(serviceApi, networkState, serverResponse)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun provideDataStoreCache(@ApplicationContext context: Context): DataStoreCache {
        return DataStoreCache(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(service: Service, dataStoreCache: DataStoreCache): IUserRepository {
        return UserRepositoryImpl(service, dataStoreCache)
    }

    @Provides
    @Singleton
    fun provideArticlesRepository(service: Service): IArticlesRepository {
        return ArticlesRepositoryImpl(service)
    }
}