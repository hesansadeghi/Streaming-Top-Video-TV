package com.example.streaming_top_video_tv.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.streaming_top_video_tv.data.db.dao.DownloadedMoviesDao
import com.example.streaming_top_video_tv.data.db.dao.FavoriteMoviesDao
import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity
import com.example.streaming_top_video_tv.data.db.entities.FavoriteMovieEntity


@Database(
    entities = [DownloadedMovieEntity::class, FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConvertorCustom::class)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun downloadedMoviesDao(): DownloadedMoviesDao

    abstract fun favoriteMoviesDao(): FavoriteMoviesDao

}