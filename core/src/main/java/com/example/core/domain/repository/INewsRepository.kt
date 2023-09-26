package com.example.core.domain.repository

import com.example.core.data.source.Resource
import com.example.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getAllNews(country: String): Flow<Resource<List<News>>>
    fun getFavorite(): Flow<List<News>>
    fun setFavoriteTourism(news: News, state: Boolean)
}