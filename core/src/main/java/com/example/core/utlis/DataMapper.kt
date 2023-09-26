package com.example.core.utlis

import com.example.core.data.local.entity.NewsEntity
import com.example.core.data.network.response.ArticlesItem
import com.example.core.domain.model.News

object DataMapper {
    fun mapResponsesToEntities(input: List<ArticlesItem?>?): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        input?.map {
            val news = NewsEntity(
                /*publishedAt = it!!.publishedAt,
                author = it.author,
                urlToImage = it.urlToImage,
                description = it.description,
                title = it.title,
                url = it.url,
                content = it.content,
                isFavorite = false*/

                publishedAt = it!!.publishedAt ?: "",
                author = it.author ?: "",
                urlToImage = it.urlToImage ?: "",
                description = it.description ?: "",
                title = it.title ?: "",
                url = it.url ?: "",
                content = it.content ?: "",
                isFavorite = false
            )
            newsList.add(news)
        }
        return newsList
    }

    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map {
            News(
                id = it.id.toInt(),
                publishedAt = it.publishedAt,
                author = it.author,
                urlToImage = it.urlToImage,
                description = it.description,
                title = it.title,
                url = it.url,
                content = it.content,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: News) = NewsEntity(
        id = input.id.toInt(),
        publishedAt = input.publishedAt,
        author = input.author,
        urlToImage = input.urlToImage,
        description = input.description,
        title = input.title,
        url = input.url,
        content = input.content,
        isFavorite = input.isFavorite
    )
}