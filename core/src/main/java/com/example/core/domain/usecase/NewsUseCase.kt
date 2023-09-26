package com.example.core.domain.usecase

import com.example.core.data.source.Resource
import com.example.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getAllNews(country: String): Flow<Resource<List<News>>>
    fun getFavorite(): Flow<List<News>>
    fun setFavoriteTourism(news: News, state: Boolean)
}