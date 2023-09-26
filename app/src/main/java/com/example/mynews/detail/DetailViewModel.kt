package com.example.mynews.detail

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.News
import com.example.core.domain.usecase.NewsUseCase

class DetailViewModel(private val newsUseCase: NewsUseCase) :ViewModel(){
    fun addFavorite(news: News, newStatus: Boolean){
        return newsUseCase.setFavoriteTourism(news,newStatus)
    }
}