package com.example.mynews.di

import com.example.core.domain.usecase.NewsInteactor
import com.example.core.domain.usecase.NewsUseCase
import com.example.mynews.detail.DetailViewModel
import com.example.mynews.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteactor(get()) }
}

val viewModelModule = module {
    viewModel { (country: String) -> NewsViewModel(get(), country) }
    viewModel { DetailViewModel(get()) }
}