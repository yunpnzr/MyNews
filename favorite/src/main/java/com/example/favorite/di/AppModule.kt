package com.example.favorite.di

import com.example.favorite.main.FavoriteMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module{
    viewModel { FavoriteMainViewModel(get()) }
}