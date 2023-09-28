package com.example.core.data.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "publishedAt")
    var publishedAt: String,

    @ColumnInfo(name = "author")
    var author: String,

    @ColumnInfo(name = "urlToImage")
    var urlToImage: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
):Parcelable