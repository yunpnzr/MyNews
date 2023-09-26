package com.example.favorite.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.NewsUseCase

class FavoriteMainViewModel (useCase: NewsUseCase): ViewModel(){
    val favoriteNews = useCase.getFavorite().asLiveData()
}