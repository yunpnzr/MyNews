package com.example.core.data.local

import com.example.core.data.local.db.NewsDao
import com.example.core.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val newsDao: NewsDao) {
    fun getAllNews(): Flow<List<NewsEntity>> = newsDao.getAllNews()
    fun getFavorite(): Flow<List<NewsEntity>> = newsDao.getFavoriteNews()
    fun insertNews(newsList: List<NewsEntity>) = newsDao.insertNews(newsList)
    fun setFavoriteNews(news: NewsEntity, newState: Boolean) {
        news.isFavorite = newState
        newsDao.updateFavoriteNews(news)
    }
}