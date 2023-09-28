package com.example.core.di

import androidx.room.Room
import com.example.core.data.local.LocalDataSource
import com.example.core.data.local.db.NewsDB
import com.example.core.data.network.RemoteDataSource
import com.example.core.data.network.api.ApiService
import com.example.core.data.source.NewsRepository
import com.example.core.domain.repository.INewsRepository
import com.example.core.utlis.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val hostname = "newsapi.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/3zBmqXH8RxlLbD8WUZQIcgaUohZ0QxprXZrYvFGqWko=")
            .add(hostname, "sha256/hS5jJ4P+iQUErBkvoWBQOd1T7VOAYlOVegvv1iMzpxA=")
            .add(hostname, "sha256/A+L5NEZLzX9+Tzc2Y5TMTKjdRlaasLKndpTU0hrW6jI=")
            .add(hostname, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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
        val pass: ByteArray = SQLiteDatabase.getBytes("yunpznr".toCharArray())
        val factory = SupportFactory(pass)
        Room.databaseBuilder(
            androidContext(), NewsDB::class.java, "News.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<INewsRepository> { NewsRepository(get(), get(), get()) }
}