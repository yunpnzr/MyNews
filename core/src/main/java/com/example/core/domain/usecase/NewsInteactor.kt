package com.example.core.domain.usecase

import com.example.core.data.source.Resource
import com.example.core.domain.model.News
import com.example.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class NewsInteactor(private val newsRepository: INewsRepository): NewsUseCase {
    override fun getAllNews(country: String): Flow<Resource<List<News>>> {
        return newsRepository.getAllNews(country)
    }

    override fun getFavorite(): Flow<List<News>> {
        return newsRepository.getFavorite()
    }

    override fun setFavoriteTourism(news: News, state: Boolean) {
        return newsRepository.setFavoriteTourism(news,state)
    }
}