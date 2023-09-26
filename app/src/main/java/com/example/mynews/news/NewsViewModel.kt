package com.example.mynews.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.NewsUseCase

class NewsViewModel(newsUseCase: NewsUseCase, country:String): ViewModel() {
    val news = newsUseCase.getAllNews(country).asLiveData()
}