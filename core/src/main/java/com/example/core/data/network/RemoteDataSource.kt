package com.example.core.data.network

import android.util.Log
import com.example.core.data.network.api.ApiResponse
import com.example.core.data.network.api.ApiService
import com.example.core.data.network.response.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource (private val apiService: ApiService) {
    suspend fun getNews(country: String): Flow<ApiResponse<List<ArticlesItem?>?>> {
        return flow {
            try{
                val response = apiService.getNews(country)
                val data = response.articles
                if (data?.isNotEmpty() == true){
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}