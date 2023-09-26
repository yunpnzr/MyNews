package com.example.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDB: RoomDatabase() {
    abstract fun newsDao() : NewsDao
}