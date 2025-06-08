package com.example.xpivo.feature.articles_page

import android.content.Context
import com.example.xpivo.core.view_model.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(@ApplicationContext context: Context, private val articlesRepository: IArticlesRepository) : BaseViewModel(context) {
}