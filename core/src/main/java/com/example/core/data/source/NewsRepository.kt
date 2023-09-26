package com.example.core.data.source

import com.example.core.data.local.LocalDataSource
import com.example.core.data.network.RemoteDataSource
import com.example.core.data.network.api.ApiResponse
import com.example.core.data.network.response.ArticlesItem
import com.example.core.domain.model.News
import com.example.core.domain.repository.INewsRepository
import com.example.core.utlis.AppExecutors
import com.example.core.utlis.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NewsRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : INewsRepository {
    override fun getAllNews(country: String): Flow<Resource<List<News>>> {
        return object : NetworkBoundResource<List<News>, List<ArticlesItem>>(),
            Flow <Resource<List<News>>>{
            override fun loadFromDB(): Flow<List<News>> {
                return localDataSource.getAllNews().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ArticlesItem?>?>> {
                return remoteDataSource.getNews(country)
            }

            override suspend fun saveCallResult(data: List<ArticlesItem?>?) {
                withContext(Dispatchers.IO){
                    localDataSource.insertNews(DataMapper.mapResponsesToEntities(data))
                }
            }

            override fun shouldFetch(data: List<News>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun collect(collector: FlowCollector<Resource<List<News>>>) {

            }

        }.asFlow()

    }

    override fun getFavorite(): Flow<List<News>> {
        return localDataSource.getFavorite().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteTourism(news: News, state: Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteNews(newsEntity,state)
        }
    }
}