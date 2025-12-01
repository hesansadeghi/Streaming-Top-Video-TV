package com.example.streaming_top_video_tv.data.repositories.interfaces

import com.example.streaming_top_video_tv.data.db.entities.FavoriteMovieEntity

interface InsertFavoriteMovieRepository {

    suspend fun insertFavoriteMovie(entity: FavoriteMovieEntity)
}