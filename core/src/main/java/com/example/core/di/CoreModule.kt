package com.example.core.di

import androidx.room.Room
import com.example.core.data.local.LocalDataSource
import com.example.core.data.local.db.NewsDB
import com.example.core.data.network.RemoteDataSource
import com.example.core.data.network.api.ApiService
import com.example.core.data.source.NewsRepository
import com.example.core.domain.repository.INewsRepository
import com.example.core.utlis.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val dbModule = module {
    factory { get<NewsDB>().newsDao() }
    single {
        Room.databaseBuilder(
            androidContext(), NewsDB::class.java, "News.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<INewsRepository> { NewsRepository(get(), get(), get()) }
}