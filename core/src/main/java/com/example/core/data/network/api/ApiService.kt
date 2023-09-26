package com.example.core.data.network.api

import com.example.core.BuildConfig
import com.example.core.data.network.response.ListNewsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String
    ):ListNewsResponse
}